package com.rip.load.utils;

import com.alibaba.fastjson.JSON;
import com.rip.load.otherPojo.city.City;
import com.rip.load.otherPojo.city.CityJson;
import com.rip.load.otherPojo.city.Province;
import com.rip.load.otherPojo.city.Area;
import com.rip.load.pojo.RegionArea;
import com.rip.load.pojo.RegionCity;
import com.rip.load.pojo.RegionProvince;
import com.rip.load.service.RegionAreaService;
import com.rip.load.service.RegionCityService;
import com.rip.load.service.RegionProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.ObjectName;
import javax.sql.rowset.CachedRowSet;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

@Component
public class Test {
    /*public static void main(String[] args) {
        String name;
        if(args.length > 0)
            name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }

        try {
            Class cl = Class.forName(name);
            Class supercl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if(modifiers.length() > 0 ){
                System.out.println("类的修饰符： " + modifiers + "  ");
            }
            System.out.println("类名:  " + name);
            if(supercl != null && supercl != Object.class){
                System.out.println(" 父类类名 " + supercl.getName());
            }

            System.out.println("****构造器***************************************");
            printConstructors(cl);
            System.out.println("****方法**************************************");
            printMethods(cl);
            System.out.println("****域**************************************");
            printFields(cl);
            System.out.println("********************************************");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);



    }

    public static void printConstructors(Class cl){
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor c : constructors){
            String name = c.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(name + "(");

            Class[] paramTypes = c.getParameterTypes();
            for(int j = 0; j < paramTypes.length; j++){
                if(j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    public static void printMethods(Class cl){
        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods){
            Class retType = m.getReturnType();
            String name = m.getName();

            System.out.print("  ");

            String modifiers = Modifier.toString(m.getModifiers());

            if(modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            Class[] parameterTypes = m.getParameterTypes();
            for(int j = 0; j < parameterTypes.length; j++){
                if(j > 0 ) System.out.print(", ");
                System.out.print(parameterTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    public static void printFields(Class cl){
        Field[] fields = cl.getDeclaredFields();
        for(Field f : fields){
            Class type = f.getType();
            String name = f.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " " );
            System.out.println(type.getName() + " " + name + ";");
        }

    }*/

    @Autowired
    RegionProvinceService regionProvinceService;
    @Autowired
    RegionCityService regionCityService;
    @Autowired
    RegionAreaService regionAreaService;

    public void k(){
        Province province = JSON.parseObject(CityJson.cityJson, Province.class);
        List<City> cityList = province.getCityList();

        RegionProvince regionProvince = new RegionProvince();
        regionProvince.setCode(province.getCode());
        regionProvince.setName(province.getName());
        boolean insert = regionProvinceService.insert(regionProvince);

        for (City city : cityList){
            RegionCity regionCity = new RegionCity();
            regionCity.setCode(city.getCode());
            regionCity.setName(city.getName());
            regionCity.setSuperId(regionProvince.getId());
            boolean insert1 = regionCityService.insert(regionCity);
            List<RegionArea> list = new ArrayList<>();
            for(Area area : city.getAreaList()){
                RegionArea regionArea = new RegionArea();
                regionArea.setCode(area.getCode());
                regionArea.setName(area.getName());
                regionArea.setSuperId(regionCity.getId());
                list.add(regionArea);
            }
            regionAreaService.insertBatch(list);
        }
    }

    public static void main(String[] args) {

        new Test().k();


    }

}
