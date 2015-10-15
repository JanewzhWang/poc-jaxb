package common.utils;

import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class CommonUtils {

	public static <T> String marshal(T cls) {
		try {
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(cls.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cls, writer);
			return writer.toString();
		} catch (JAXBException ex) {
			throw new RuntimeException("Error serializing object into output stream", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(InputStream inputStream, Class<T> cls) {
		try {
            JAXBContext context = JAXBContext.newInstance(cls);
            Unmarshaller u = context.createUnmarshaller();
            return (T)u.unmarshal(inputStream);
        } catch (JAXBException ex) {
            throw new RuntimeException("Error deserializing input stream into object", ex);
        }
	}
	
}
