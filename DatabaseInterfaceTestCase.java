import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the database Interface.
 *
 * <p>Purdue University -- CS18000 -- Fall 2024</p>
 *
 * @author Purdue CS
 * @version November 2, 2024
 */


public class DatabaseInterfaceTestCase {
    @Test(timeout = 1000)
    public void classDeclarationTestOne() {
        Class<?> dataclass = DatabaseInterface.class;
        int modifiers;
        Class<?> superclass;
        Class<?>[] superinterfaces;

        //testing if methods are there that should be

        try {
            Method saveData = dataclass.getMethod("saveData");
            assertNotNull("The Message interface should have an 'saveData' method.", saveData);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'saveData' method.");
        }
        try {
            Method delete = dataclass.getMethod("deleteData", String.class , String.class);
            assertNotNull("The Message interface should have an 'deleteData' method.", delete);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'deleteData' method.");
        }
        try {
            Method change = dataclass.getMethod("ChangeData", String.class,Object.class , String.class);
            assertNotNull("The Message interface should have an 'ChangeData' method.", change);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'ChangeData' method.");
        }
        try {
            Method get = dataclass.getMethod("getData",String.class , String.class);
            assertNotNull("The Message interface should have an 'getData' method.", get);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'getData' method.");
        }

        try {
            Method initialize = dataclass.getMethod("initializeDatabase");
            assertNotNull("The Message interface should have an 'initializeDatabase' method.", initialize);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'initializeDatabase' method.");
        }

        try {
            Method load = dataclass.getMethod("LoadOldData");
            assertNotNull("The Message interface should have an 'LoadOldData' method.", load);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'LoadOldData' method.");
        }
        try {
            Method save = dataclass.getMethod("saveData");
            assertNotNull("The Message interface should have an 'saveData' method.", save);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'saveData' method.");
        }
        try {
            Method loadtable = dataclass.getMethod("loadTableMessage", String.class);
            assertNotNull("The Message interface should have an 'loadTableMessage' method.", loadtable);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'loadTableMessage' method.");
        }
        try {
            Method loadtable = dataclass.getMethod("loadTableChat", String.class);
            assertNotNull("The Message interface should have an 'loadTableChat' method.", loadtable);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'loadTableChat' method.");
        }
        try {
            Method loadtable = dataclass.getMethod("loadTableUser", String.class);
            assertNotNull("The Message interface should have an 'loadTableUser' method.", loadtable);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'loadTableUser' method.");
        }
        try {
            Method saveTable = dataclass.getMethod("saveTableMessage", Map.class, String.class);
            assertNotNull("The Message interface should have an 'saveTableMessage' method.", saveTable);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'saveTableMessage' method.");
        }
        try {
            Method saveTable = dataclass.getMethod("saveTableChat", Map.class, String.class);
            assertNotNull("The Message interface should have an 'saveTableChat' method.", saveTable);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'saveTableChat' method.");
        }
        try {
            Method saveTable = dataclass.getMethod("saveTableUser", Map.class, String.class);
            assertNotNull("The Message interface should have an 'saveTableUser' method.", saveTable);
        } catch (NoSuchMethodException e) {
            fail("The Message interface is missing the 'saveTableUser' method.");
        }

        modifiers = dataclass.getModifiers();

        superclass = dataclass.getSuperclass();

        superinterfaces = dataclass.getInterfaces();

        Assert.assertTrue("Ensure that 'Message Interface' is public", Modifier.isPublic(modifiers));

        Assert.assertTrue("Ensure that `Message Interface` is NOT `abstract`!", Modifier.isAbstract(modifiers));

        Assert.assertEquals("Ensure that Message Interface has no interfaces itself" , 0, superinterfaces.length);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DatabaseInterfaceTestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

    }

}


