Feature: Sending message to group

  Scenario: Test sending email to group from Inbox
    Given I am on main page mail.ru
    And I am signed in to account with login "sid-vlada@mail.ru" and password "18091991mail"
    When I click the button create new message
    And Send it to "sid-vlada@yandex.by, ligadema.vs@gmail.com" with subject "test" and text "Hi!"
    Then Confirmation phrase "Sent" is displayed