package com.example.android.java.model;

public interface PersonInterface {

    default int compareByAge(Person o1, Person o2) {
        Integer age_1 = o1.getAge();
        return age_1.compareTo(o2.getAge());
    }
}
