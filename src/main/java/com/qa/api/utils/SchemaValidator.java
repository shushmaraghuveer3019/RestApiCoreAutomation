package com.qa.api.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SchemaValidator {

    public static boolean validateApiSchema(Response response, String schemaFileName) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFileName));
            System.out.println("Schema Validation is Passed");
            return true;
        }
        catch (Exception e){
            System.out.println("Schema Validation is Failed.."+e.getMessage());
            return false;
        }

    }
}
