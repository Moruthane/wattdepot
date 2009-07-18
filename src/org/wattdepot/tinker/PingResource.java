package org.wattdepot.tinker;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

/**
 * It's the machine that goes ping! http://www.youtube.com/watch?v=NcHdF1eHhgc&feature=channel_page
 * 
 * @author Robert Brewer
 */
public class PingResource extends Resource {

  public PingResource(Context context, Request request, Response response) {
    super(context, request, response);

    // This representation has only one type of representation.
    getVariants().add(new Variant(MediaType.TEXT_PLAIN));
  }

  /**
   * Returns a full representation for a given variant.
   */
  @Override
  public Representation represent(Variant variant) throws ResourceException {
    Representation representation = new StringRepresentation("Hello World!", MediaType.TEXT_PLAIN);
    return representation;
  }

}
