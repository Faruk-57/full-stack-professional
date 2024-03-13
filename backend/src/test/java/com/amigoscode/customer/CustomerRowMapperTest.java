package com.amigoscode.customer;


import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerRowMapperTest {


    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(37);
        when(resultSet.getString("name")).thenReturn("Faruk");
        when(resultSet.getString("email")).thenReturn("faruk@test.com");

        //When
        Customer actual = customerRowMapper.mapRow(resultSet,1);

        //Then
        Customer expected = new Customer(1,"Faruk", "faruk@test.com", 37);

        assertThat(actual).isEqualTo(expected);

    }
}