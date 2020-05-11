package pl.edu.agh.soa.lab1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import protobuf.LegoSetsProtoBuf.LegoSetId;
import protobuf.LegoSetsProtoBuf.LegoSetsIdProtoBuf;

@Provider
@Produces("application/protobuf")
@Consumes("application/protobuf")
public class WidgetProtocMessageBodyProvider
        implements MessageBodyReader, MessageBodyWriter {

    @Override
    public boolean isReadable(Class type, Type type1,
                              Annotation[] antns, MediaType mt) {
        return LegoSetId.class.isAssignableFrom(type)
                || LegoSetsIdProtoBuf.class.isAssignableFrom(type);
    }

    @Override
    public Object readFrom(Class type, Type type1, Annotation[] antns,
                           MediaType mt, MultivaluedMap mm, InputStream in)
            throws IOException, WebApplicationException {
        if (LegoSetId.class.isAssignableFrom(type)) {
            return LegoSetId.parseFrom(in);
        } else if (LegoSetsIdProtoBuf.class.isAssignableFrom(type)) {
            return LegoSetsIdProtoBuf.parseFrom(in);
        } else {
            throw new BadRequestException("Can't Deserailize");
        }
    }

    @Override
    public boolean isWriteable(Class type, Type type1,
                               Annotation[] antns, MediaType mt) {
        return LegoSetId.class.isAssignableFrom(type)
                || LegoSetsIdProtoBuf.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Object t, Class type, Type type1,
                        Annotation[] antns, MediaType mt) {  return -1; }

    @Override
    public void writeTo(Object t, Class type, Type type1,
                        Annotation[] antns, MediaType mt,
                        MultivaluedMap mm, OutputStream out)
            throws IOException, WebApplicationException {
        if (t instanceof LegoSetId) {
            LegoSetId widget = (LegoSetId)t;
            widget.writeTo(out);
        } else if (t instanceof LegoSetsIdProtoBuf) {
            LegoSetsIdProtoBuf list = (LegoSetsIdProtoBuf)t;
            list.writeTo(out);
        }
    }
}