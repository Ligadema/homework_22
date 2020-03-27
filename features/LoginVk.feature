Feature: Login to vk.com

  Scenario: Successful login to vk.com with correct login name and password
    Given I am on main page vk.com
    And I am signed in to account with login "sid-vlada@yandex.ru" and password "12345"
    Then Confirmation phrase "You logged in!" is displayed