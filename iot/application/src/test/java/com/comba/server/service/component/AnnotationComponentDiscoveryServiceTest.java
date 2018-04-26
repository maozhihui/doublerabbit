package com.comba.server.service.component;


import com.comba.server.common.data.plugin.ComponentDescriptor;
import com.comba.server.common.data.plugin.ComponentType;
import com.comba.server.extensions.api.component.Filter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * AnnotationComponentDiscoveryService Test
 *
 * @author maozhihui
 * @create 2017-09-29 15:11
 **/
@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application.properties"})
@SpringBootTest()
public class AnnotationComponentDiscoveryServiceTest {

    @Autowired
    private AnnotationComponentDiscoveryService componentService;

    @Test
    public void testGetBeanDefinitions(){
        try {
            Method method = componentService.getClass().getDeclaredMethod("getBeanDefinitions",Class.class);
            method.setAccessible(true);
            Set<BeanDefinition> res = (Set<BeanDefinition>)method.invoke(componentService, Filter.class);
            assertTrue(res.size()>0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Set<BeanDefinition> getBeanDefinitions(){
        Set<BeanDefinition> res = new HashSet<>();
        try {
            Method method = componentService.getClass().getDeclaredMethod("getBeanDefinitions",Class.class);
            method.setAccessible(true);
            res = (Set<BeanDefinition>)method.invoke(componentService, Filter.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Test
    public void testPersist(){
        try {
            Method method = componentService.getClass().getDeclaredMethod("persist",Set.class, ComponentType.class);
            method.setAccessible(true);
            List<ComponentDescriptor> res = (List<ComponentDescriptor>)method.invoke(componentService,getBeanDefinitions(),ComponentType.FILTER );
            System.out.println("ComponentDescriptor:"+res.size());
            assertTrue(res.size()>0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
