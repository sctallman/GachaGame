package com.group3.data;


import java.util.Date;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.group3.beans.Event;

public interface EventRepository extends ReactiveCassandraRepository<Event, /*PRIMARY KEY DATATYPE REPLACES String PLACEHOLDER*/ Date> {


}
