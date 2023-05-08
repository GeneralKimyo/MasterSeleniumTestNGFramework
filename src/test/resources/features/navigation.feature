Feature: Navigation
    As a user
    I want to navigate every pages
    So that I can use other features of website

Scenario: Successful navigation to Store Page
    Given I am on the home page
    When I click on store menu link
    Then I should see the store page