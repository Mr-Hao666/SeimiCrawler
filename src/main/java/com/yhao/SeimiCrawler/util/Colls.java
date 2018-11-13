package com.yhao.SeimiCrawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 集合数组工具
 *
 * @author long.hua
 * @version 1.0.0
 * @since 2016-01-21 00:24
 */
public class Colls {

    private final static Logger LOGGER = LoggerFactory.getLogger(Colls.class);


    public static <T> boolean notEmpty(Collection<T> colls) {
        return !isEmpty(colls);
    }

    public static <T> boolean isEmpty(Collection<T> colls) {
        return colls == null || colls.isEmpty();
    }

    public static <K, V> boolean notEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean notEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 原始类型的数组需要转换为其包装类型的数组
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    @SafeVarargs
    public static <T> List<T> array2List(T... t) {
        if (isEmpty(t)) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>(t.length);

        Collections.addAll(list, t);

        return list;
    }

    @SafeVarargs
    public static <T> Set<T> array2Set(T... t) {
        if (isEmpty(t)) {
            return Collections.emptySet();
        }

        Set<T> set = new HashSet<>(t.length);

        Collections.addAll(set, t);

        return set;
    }

    public static <K, V> Map<K, V> array2Map(K[] k, V[] v) {
        if (size(k) <= 0 || size(k) != size(v)) {
            return Collections.emptyMap();
        }

        Map<K, V> map = new HashMap<>(k.length);

        for (int i = 0; i < k.length; i++) {
            map.put(k[i], v[i]);
        }

        return map;
    }

    @SafeVarargs
    public static <T> Map<T, T> array2Map(T[]... t) {
        if (isEmpty(t)) {
            return Collections.emptyMap();
        }

        Map<T, T> map = new HashMap<>(t.length);

        for (T[] a : t) {
            if (isEmpty(a) || a.length < 2) {
                throw new RuntimeException("array to map error, the length little 2!");
            }
            map.put(a[0], a[1]);
        }

        return map;
    }

    public static <T> List<T> toList(Collection<T>... sets) {
        if (isEmpty(sets)) {
            return Collections.emptyList();
        }

        int size = 0;
        for (Collection<T> set : sets) {
            size += set.size();
        }

        List<T> list = new ArrayList<>(size);
        for (Collection<T> set : sets) {
            if (notEmpty(set)) {
                list.addAll(set);
            }
        }

        return list;
    }

    public static <T> Set<T> toSet(Collection<T>... lists) {
        if (isEmpty(lists)) {
            return Collections.emptySet();
        }

        int size = 0;
        for (Collection<T> set : lists) {
            size += set.size();
        }

        Set<T> set = new HashSet<>(size);

        for (Collection<T> list : lists) {
            if (notEmpty(list)) {
                set.addAll(list);
            }
        }

        return set;
    }

    @SuppressWarnings("unchecked")
    public static <K, T> Map<K, T> toMap(Collection<T> list, String fieldName4Key) {
        if (Colls.isEmpty(list) || fieldName4Key == null || fieldName4Key.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, T> map = new HashMap<>(list.size());

        for (T t : list) {
            if (t != null) {

                K key = getKey(t, fieldName4Key);
                if (key != null) {

                    map.put(key, t);
                }
            }
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    public static <K, T, V> Map<K, V> toMap(Collection<T> list, String fieldName4Key, String fieldName4Value) {
        if (Colls.isEmpty(list)
                || fieldName4Key == null || fieldName4Key.isEmpty()
                || fieldName4Value == null || fieldName4Value.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, V> map = new HashMap<>();

        for (T t : list) {
            if (t != null) {

                K key = getKey(t, fieldName4Key);
                V value = getKey(t, fieldName4Value);
                if (key != null && value != null) {

                    map.put(key, value);
                }
            }
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    public static <K, T> Map<K, List<T>> toListMap(Collection<T> list, String fieldName) {
        return toListMap(list, fieldName, false);
    }

    /**
     * 把list转换以指定属性值为key的map
     *
     * @param list
     * @param fieldName
     * @param distinct  是否要去重
     * @param <K>       以属性值为map的Key
     * @param <T>       带指定属性名的对象
     * @return
     */
    public static <K, T> Map<K, List<T>> toListMap(Collection<T> list, String fieldName, boolean distinct) {
        if (isEmpty(list) || fieldName == null || fieldName.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, List<T>> map = new HashMap<>();

        for (T t : list) {

            K key = getKey(t, fieldName);
            if (key != null) {

                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<T>());
                }

                if (!distinct) {
                    map.get(key).add(t);
                } else {
                    if (!map.get(key).contains(t)) {
                        map.get(key).add(t);
                    }
                }
            }
        }

        return map;
    }

    public static <K, T> Map<K, Set<T>> toSetMap(Collection<T> list, String fieldName) {
        if (isEmpty(list) || fieldName == null || fieldName.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, Set<T>> map = new HashMap<>();

        for (T t : list) {
            K key = getKey(t, fieldName);
            if (key != null) {

                if (!map.containsKey(key)) {
                    map.put(key, new HashSet<T>());
                }

                map.get(key).add(t);
            }

        }

        return map;
    }


    public static <K, T> List<K> toList(Collection<T> list, String fieldName) {
        if (isEmpty(list) || fieldName == null || fieldName.isEmpty()) {
            return Collections.emptyList();
        }

        List<K> keys = new ArrayList<>(list.size());

        for (T t : list) {

            K key = getKey(t, fieldName);
            if (key != null) {

                keys.add(key);
            }
        }

        return keys;
    }

    public static <K, T> Set<K> toSet(Collection<T> list, String fieldName) {
        if (isEmpty(list) || fieldName == null || fieldName.isEmpty()) {
            return Collections.emptySet();
        }

        Set<K> keys = new HashSet<>();

        for (T t : list) {

            K key = getKey(t, fieldName);
            if (key != null) {

                keys.add(key);
            }
        }

        return keys;
    }

    @SuppressWarnings("unchecked")
    public static <K> K getKey(Object t, String fieldName) {
        if (t instanceof Map) {
            return (K) ((Map) t).get(fieldName);

        } else {
            for (Class<?> clazz = t.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (K) field.get(t);
                } catch (Exception e) {
                    // 如果往外抛就不会执行clazz = clazz.getSuperclass()，最后就不会进入到父类中了
                }
            }
        }

        return null;
    }

    public static <T> Collection<T> filterNull(Collection<T> coll) {
        if (notEmpty(coll)) {

            Iterator<T> iter = coll.iterator();
            while (iter.hasNext()) {
                if (iter.next() == null) {
                    iter.remove();
                }
            }
        }

        return coll;
    }

    /***
     * 乱序可能会变
     *
     * @param coll
     * @param <T>
     * @return
     */
    public static <T> Collection<T> filterRepeat(Collection<T> coll) {
        if (notEmpty(coll)) {

            Set<T> set = new HashSet<>();
            for (T t : coll) {
                set.add(t);
            }
            coll.clear();
            coll.addAll(set);
        }

        return coll;
    }

    /**
     * 过滤掉在source中存在的targets中的元素
     *
     * @param targets 需要被过滤的列表
     * @param sources 提供判断是否重复的列表
     * @param <T>     元素类型
     */
    public static <T> Collection<T> filterRepeat(Collection<T> targets, Collection<T> sources) {
        if (isEmpty(targets) || isEmpty(sources)) {
            return targets;
        }

        targets.removeAll(sources);

        return targets;
    }

    public static <T> Collection<T> filterRepeat(Collection<T> targets, String compareField, Collection<T> sources) {
        return filterRepeat(targets, compareField, sources, compareField);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private static <T> boolean isOneType(Collection<T> targets) {
        boolean oneType = true;

        T t = null;
        for (T tmp : targets) {
            t = tmp;
            break;
        }
        if (t != null) {

            String name = t.getClass().getCanonicalName();
            for (T tmp : targets) {
                if (!tmp.getClass().getCanonicalName().equals(name)) {
                    oneType = false;
                    break;
                }
            }
        }

        return oneType;
    }

    private static <T> boolean isBaseType(Collection<T> targets) {
        boolean baseType = true;

        for (T t : targets) {
            if (t != null) {
                if (!(t instanceof String
                        || t instanceof Number
                        || t instanceof Boolean
                        || t instanceof Character)) {
                    baseType = false;
                    break;

                }
            }
        }

        return baseType;
    }

    /**
     * 过滤掉两个集合中指定属性名的值相同的元素
     *
     * @param targets            将被移除相同属性值的元素的列表
     * @param targetCompareField 列表元素的属性名
     * @param sources            用于对比移除相同属性值的元素的列表
     * @param sourceCompareField 列表元素的属性名
     * @param <M>                targets列表的元素类型
     * @param <N>                sources列表的元素类型
     * @return 移除相同之后的列表
     */
    public static <M, N> Collection<M> filterRepeat(Collection<M> targets, String targetCompareField, Collection<N> sources, String sourceCompareField) {
        if (isEmpty(sources) || isEmpty(targetCompareField)
                || isEmpty(targets) || isEmpty(sourceCompareField)) {
            return targets;
        }

        // 如果集合元素全为普通类型 并 全为一种类型元素，则根据对比字段来过滤
        if (isOneType(targets) && isOneType(sources) && !isBaseType(targets) && !isBaseType(sources)) {

            Set<Object> sourceKeys = toSet(sources, sourceCompareField);

            Iterator<M> iter = targets.iterator();
            while (iter.hasNext()) {
                M target = iter.next();

                Object key = getKey(target, targetCompareField);
                if (sourceKeys.contains(key)) {
                    iter.remove();
                }
            }

        } else {
            LOGGER.warn("Input targets and sources list's element not all Object type!");
        }

        return targets;
    }

    public static <T> List<T> subList(List<T> list, int offset, int pageSize) {
        // 开始结束位置
        int begin = offset > list.size() ? list.size() : offset;
        int end = (offset + pageSize) > list.size() ? list.size() : (offset + pageSize);

        // 返回分页数据
        return new ArrayList<>(list.subList(begin, end));
    }

    public static <T> int size(T[] ts) {
        return ts == null ? 0 : ts.length;
    }

    public static <K, V> int size(Map<K, V> map) {
        return map == null ? 0 : map.size();
    }

    public static <T> int size(Collection<T> colls) {
        return colls == null ? 0 : colls.size();
    }

    public static Object[] toArray(Object... t) {
        List<Object> objs = new ArrayList<>();
        for (Object o : t) {
            if (o instanceof Collection) {
                Collection tmps = (Collection) o;
                for (Object tmp : tmps) {
                    objs.add(tmp);
                }
            } else if (o instanceof Object[]) {
                Object[] tmps = (Object[]) o;
                for (Object tmp : tmps) {
                    objs.add(tmp);
                }
            } else {
                objs.add(0);
            }
        }

        return objs.toArray();
    }

    public static void main(String[] args) throws Exception {
//        Thread th1 = new Thread(new t1());
//        Thread th2 = new Thread(new t2());
//        th1.start();
//        th2.start();
//
//        th1.join();
//        th2.join();
//        System.out.println("结束");

//        List l1 = new ArrayList();
//        l1.add(1);
//        l1.add(2);
//        l1.add('b');
//        l1.add('c');
//        l1.add("d");
//        l1.add("e");
//
//        List l2 = new ArrayList();
//        l2.add(1);
//        l2.add('b');
//        l2.add("d");
//        l1.removeAll(l2);
//        System.out.println(l1);
    }
}

class Keyword {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class t1 implements Runnable {
    @Override
    public void run() {
        int s = 1000;
        List<Keyword> list = new ArrayList<>(s);
        for (int i = 0; i < s; i++) {
            Keyword k = new Keyword();
            k.setId(i);
            list.add(k);
        }

        int l = 10000;

        long b = System.currentTimeMillis();
        for (int i = 0; i < l; i++) {
            Colls.toList(list, "id");
        }
        long e = System.currentTimeMillis();
        System.out.println("t1 cost:" + (e - b));
    }
}

class t2 implements Runnable {
    @Override
    public void run() {
        int s = 1000;
        List<Keyword> list0 = new ArrayList<>(s);
        for (int i = 0; i < s; i++) {
            Keyword k = new Keyword();
            k.setId(i);
            list0.add(k);
        }

        int l = 10000;

        long b = System.currentTimeMillis();
        for (int i = 0; i < l; i++) {
            List<Integer> list1 = new ArrayList<>(s);
            for (Keyword keyword : list0) {
                list1.add(keyword.getId());
            }
        }
        long e = System.currentTimeMillis();

        System.out.println("t2 cost:" + (e - b));
    }
}