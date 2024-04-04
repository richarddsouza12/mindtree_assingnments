Feature: Restful Booking API Tests

  Background:
    * url baseUrl
    * def response = callonce read('./components/getauthtoken.feature')
    * def tokenId = response.tokenId
    # info : auth token needs to be used when we put-update and patch-update , same token needs to be used)
    # retrieved with callonce

  Scenario: List all booking ids
    Given path '/booking'
    When method get
    Then status 200
    And match response contains '#[]'

  # Combined all these as this is a single flow of one test scenario.
  # Tried to pass varriables form resposese across scenarios but was not able to.
  # As per karate docs and stack overflow suggested to make groped dependent api calls in one scenario.
  Scenario: Post - Make a booking , Put-Update the same booking , Patch-Update the same booking , Get - View the booking

    # -------------------
    # Make a new booking
    # -------------------
    Given path '/booking'
    And header Accept = 'application/json'
    And header Content-Type = 'application/json'
    And request
    """
        {
        "firstname": "Richard",
        "lastname": "Dsouza",
        "totalprice": 1000,
        "depositpaid": true,
        "bookingdates": {
          "checkin": "2024-06-01",
          "checkout": "2024-06-02"
        },
        "additionalneeds": "Breakfast"
        }
    """
    When method post
    Then status 200
    * def bookingId = response.bookingid
    * print 'richard : set bookingId' , bookingId


    # -------------------
     # Put Update the booking
    # -------------------
    Given path '/booking/' + bookingId
    And header Accept = 'application/json'
    And header Content-Type = 'application/json'
    And header Cookie = 'token=' + tokenId
    # edit amount and checkout date
    And request
    """
        {
        "firstname": "Richard",
        "lastname": "Dsouza",
        "totalprice": 1200,
        "depositpaid": true,
        "bookingdates": {
          "checkin": "2024-06-01",
          "checkout": "2024-06-05"
        },
        "additionalneeds": "Breakfast"
        }
    """
    When method put
    Then status 200
    And match response.totalprice == 1200
    And match response.bookingdates.checkout == '2024-06-05'

    # -------------------------
    # Patch update the booking
    # ------------------------
    Given path '/booking/' + bookingId
    And header Accept = 'application/json'
    And header Content-Type = 'application/json'
    And header Cookie = 'token=' + tokenId
    And request
      """
          {
          "additionalneeds": "Breakfast and Brunch"
          }
      """
    When method patch
    Then status 200
    And match response.additionalneeds == "Breakfast and Brunch"
    And match response.firstname == "Richard"


    # ----------------------
    # Get - View the booking
    # ----------------------
    Given path '/booking/' + bookingId
    When header Accept = 'application/json'
    And method get
    Then status 200



    # -------------------------
    # Delete the booking
    # ------------------------
    Given path '/booking/' + bookingId
    And header Content-Type = 'application/json'
    And header Cookie = 'token=' + tokenId
    When method delete
    Then status 201
    And match response == "Created"
