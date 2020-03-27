package vk;

import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class ApiTest {

    private HttpClient client = HttpClientBuilder.create().build();

    @Test
    public void ApiTest() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.post?");
        builder.setParameter("access_token", "2b41331a95b026c9a0b1c567f6729c1a98c38da7a348c890f2b39566df245bdacdbf0f19a3f0ef4485785")
                .setParameter("owner_id", "62329261")
                .setParameter("message", "test message111");
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        String result = EntityUtils.toString(response.getEntity());

        System.out.println(result);
        String postId = result.replaceAll("\\D", "");
        junit.framework.Assert.assertTrue(postId.matches("\\d"));


        URIBuilder editPostBuilder = createBuilder("edit")
                .setParameter("message", "editedText")
                .setParameter("owner_id", "62329261")
                .setParameter("post_id", postId);
        getResponse(editPostBuilder);
        URIBuilder getEditedPostByIdBuilder = createBuilder("getById")
                .setParameter("posts", String.format("%s_%s", "62329261", postId));
        String postEditedText = getText(getEditedPostByIdBuilder);
        Assert.assertEquals("editedText", postEditedText);

        URIBuilder deletePostBuilder = createBuilder("delete")
                .setParameter("owner_id", "62329261")
                .setParameter("post_id", postId);
        String deletePostResponse = getResponse(deletePostBuilder);
        Assert.assertTrue(deletePostResponse.contains("1"));
    }

    public URIBuilder createBuilder(String action) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(String.format("https://api.vk.com/method/wall.%s?", action));
        return builder.setParameter("access_token", "2b41331a95b026c9a0b1c567f6729c1a98c38da7a348c890f2b39566df245bdacdbf0f19a3f0ef4485785").setParameter("v", "5.103");
    }

    public String getResponse(URIBuilder builder) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    public String getText(URIBuilder builder) throws IOException, URISyntaxException {
        String jsonString = getResponse(builder);
        String text = JsonPath.read(jsonString, "$.response[0].text");
        return text;
    }

    public String getPostId(URIBuilder builder) throws IOException, URISyntaxException {
        String jsonString = getResponse(builder);
        Integer postId = JsonPath.read(jsonString, "$.response.post_id");
        return String.valueOf(postId);
    }
}
