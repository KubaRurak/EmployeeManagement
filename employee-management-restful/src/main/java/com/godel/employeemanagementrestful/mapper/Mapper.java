package com.godel.employeemanagementrestful.mapper;

public interface Mapper <E,D>{


    public D mapperEntityToDto(E e);

    public E mapperDtoToEntity(D d);
}