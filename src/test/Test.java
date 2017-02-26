/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author wytsang
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Test t= new Test();
        t.run();
    }
    
    public void run(){
        Properties config= new Properties();
        InputStream in= getClass().getClassLoader().getResourceAsStream("config/config.properties");
        if(in!=null){
            try {
                config.load(in);
            } catch (IOException ex) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("File not found!");
        }   System.setProperty("webdriver.chrome.driver", config.getProperty("webdriver.chrome.driver"));
        int browserHeight= Integer.parseInt(config.getProperty("browser.height"));
        int browserWidth= Integer.parseInt(config.getProperty("browser.width"));
        String page = config.getProperty("website");
        WebDriver driver= new ChromeDriver();
        WebDriver.Window win= driver.manage().window();
        win.maximize();
        int screenHeight= win.getSize().getHeight();
        int screenWidth= win.getSize().getWidth();
        win.setSize(new Dimension(browserWidth, browserHeight));
        System.out.println("Screen: "+screenHeight+"*"+screenWidth);
        System.out.println("Browser: "+win.getSize().getHeight()+"*"+win.getSize().getWidth());
        win.setPosition(new Point(0, 0));
        driver.get(page);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        FileInputStream fis= null;
        try {
            File file = new File("D:\\ProgramData\\NetBeans\\Test\\resources\\script\\cal.js");
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String script = new String(data, "UTF-8");
            System.out.println(script);
            ((JavascriptExecutor) driver).executeScript(script);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
