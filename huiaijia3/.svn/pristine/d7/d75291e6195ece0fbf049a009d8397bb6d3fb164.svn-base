package com.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Object> {
		@Override
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
             String formattedDate = formatter.format(value);
             jgen.writeString(formattedDate);
			
		}
}