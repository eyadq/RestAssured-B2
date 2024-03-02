@minionNoAuthApi
Feature: Test Minion Rest Api

  Background:
    Given baseURI is "minions.api.base"


  @allMinions
  Scenario: Test Get All Minions
    Given baseURI is "minions.api.base"
    And request header Accept is "application/json"
    When with path "/api/minions"
    And the HTTP method is GET
    Then status code will be 200
    And response header Content-Type is "application/json"
    And Json response will be validated with the  "AllMinionsSchema" Schema file

    @singleMinion
  Scenario: Test Get Single Minion
    And request header Accept is "application/json"
    When with path params
      | path     | /api/minions/{minionId} |
      | minionId | 1                       |
    When the HTTP method is GET
    Then status code will be 200
    And response header Content-Type is "application/json"
    And Json response will be validated with the  "SingleMinionSchema" Schema file


      @postMinion
  Scenario: Post New Minion
    And request header Accept is "application/json"
    And request Content-Type Accept is "application/json"
    And with request body of randomized payload
    When with path "/api/minions"
    And the HTTP method is POST
    Then status code will be 201
    And response header Content-Type is "application/json"
    And Json response will be validated with the  "MinionPostSchema" Schema file
    And delete the new Minion


        @putMinion
  Scenario: Put New Minion
    And create new minion that will be updated
    And request header Accept is "application/json"
    And request Content-Type Accept is "application/json"
    And with request body of this non random payload
      | name   | Dixie Kong |
      | gender | Female     |
      | phone  | 7777777777 |
    When with path params
      | path | /api/minions/{minionId} |
    And the HTTP method is PUT
    Then status code will be 204
    And get minion and validate minion has these values
      | name   | Dixie Kong |
      | gender | Female     |
      | phone  | 7777777777 |
    And delete the new Minion


  @patchMinion
  Scenario: Patch New Minion
    And create new minion that will be updated
    And request header Accept is "application/json"
    And request Content-Type Accept is "application/json"
    And with request body of this non random payload
      | phone | 2222222222 |
    When with path params
      | path | /api/minions/{minionId} |
    And the HTTP method is PATCH
    Then status code will be 204
    And get minion and validate minion has these values
      | phone | 2222222222 |
    And delete the new Minion
