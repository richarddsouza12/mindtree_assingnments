Feature: Retrieve Auth Token

  Background:
    * url baseUrl

  Scenario: Get auth token
    Given path '/auth'
    When header Accept = 'application/json'
    And request { "username" : '#(login_credentials.username)' , "password" : '#(login_credentials.password)' }
    And method post
    Then status 200
    * def tokenId = response.token

#    * karate.set( 'tokenId', response.token )
#    * print 'info : token obtained : ', karate.get('tokenId')
#    * def - is a local varriable for one scenario only
#    we cant use this varriable for the next scenario.
#    need to use : * karate.set('generatedId', response.id) | karate.get('generatedId')
#    dosent work too
