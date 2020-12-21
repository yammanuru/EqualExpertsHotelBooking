Feature: Hotel Booking Form

  Scenario: Successfully create a new hotel booking
    Given A User visits the hotel booking form
    And The User submits valid booking data
    And The User clicks on the save button
    Then A new booking record is created

  Scenario: Successfully delete existing hotel booking
    Given A User visits the hotel booking form
    And The User submits valid booking data
    And The User clicks on the save button
    And A new booking record is created
    When user selects delete button
    Then booking is deleted