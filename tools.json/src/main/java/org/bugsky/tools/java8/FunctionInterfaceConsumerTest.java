package org.bugsky.tools.java8;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * 接口函数  只能一个方法，当然是除了默认方法与静态方法之外
 * Created by Kaishui on 2016/8/30.
 */
public class FunctionInterfaceConsumerTest {
    @FunctionalInterface
    interface Message {
        void send(String msg);

    }
    static class PhoneMessage implements Message {
        private AtomicInteger num = new AtomicInteger(0);
        @Override
        public void send(String msg) {
            System.out.println(msg);
        }
    }
    public static void main(String[] args) {
        String name = "name";
        String password = "password";

        //java.util.function.Consumer 输入<T>，没有return
        Consumer<String> messageConsumer = message -> System.out.println("----messageConsumer-----" + message);
        Consumer<String> afterConsumer = message -> new PhoneMessage().send("------afterConsumer----" + message);
        Consumer<String> exceptionConsumer = message -> {
            System.out.println("------exceptionConsumer----" + message);
            throw new NullPointerException();
        };
        
        String separator = ",";
        Arrays.asList( "a", "b", "d" ).forEach( 
            ( String e ) -> System.out.print( e + separator ) );        

        int i = 0;
        //1. 直接输入，没有输出
        messageConsumer.accept(name + i++);
        //2. 如果执行messageConsumer.accept(password)，没有异常情况就执行afterConsumer.accept(passowrd)
        messageConsumer.andThen(afterConsumer).accept(password+ i++);
        //3. exceptionConsumer.accept(password)，异常情况就不执行afterConsumer.accept(passowrd)
        exceptionConsumer.andThen(afterConsumer).accept(password+ i++); 
        

    }
}
