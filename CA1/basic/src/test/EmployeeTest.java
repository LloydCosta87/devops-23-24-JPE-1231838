/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

public class EmployeeTest {
    @Test
    public void testEmployee(){
        //Arrange
        Employee employee = new Employee("Frodo", "Baggins", "ring bearer", 4);
        //Act
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String description = employee.getDescription();
        int jobYears = employee.getJobYears();
        //Assert
        assertEquals("Frodo", firstName);
        assertEquals("Baggins", lastName);
        assertEquals("ring bearer", description);
        assertEquals(4, jobYears);
    }

}