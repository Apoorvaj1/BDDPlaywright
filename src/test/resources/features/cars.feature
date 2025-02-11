Feature: Search different cars and fetch title

  Scenario Outline: Fetch title for different cars
    Given user navigate to carwale website
    When user mousehover to New car option
    And click on Find New Cars
    Then user able to see lists of cars
    When user clicks on "<specific_car>"
    Then user able to see "<car_title>"

    Examples:
      | specific_car  | car_title     |
      | Maruti Suzuki | Maruti Cars   |
      | Mahindra      | Mahindra Cars |
