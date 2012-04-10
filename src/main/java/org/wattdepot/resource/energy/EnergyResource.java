package org.wattdepot.resource.energy;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.util.tstamp.Tstamp;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.wattdepot.resource.WattDepotResource;

/**
 * Represents electrical energy determined by sensor data from a particular source.
 * 
 * @author Robert Brewer
 */

public class EnergyResource extends WattDepotResource {

  /** To be retrieved from the URI, or else null if not found. */
  private String startTime, endTime, interval;

  /**
   * Initialize with attributes from the Request.
   */
  @Override
  protected void doInit() {
    super.doInit();
    this.startTime =
        (String) this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("startTime");
    this.endTime =
        (String) this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("endTime");
    this.interval =
        (String) this.getRequest().getResourceRef().getQueryAsForm()
            .getFirstValue("samplingInterval");
  }

  /**
   * Returns a full representation for a given variant.
   * 
   * @param variant the requested variant of this representation
   * @return the representation of this resource
   */
  @Override
  public Representation get(Variant variant) {
    String xmlString;
    
    // If we make it here, we're all clear to send the XML: either source is public or source is
    // private but user is authorized to GET.
    if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
      if ((this.startTime == null) || (this.endTime == null)) {
        // Some bad combination of options, so just fail
        setStatusMiscError("Request could not be understood.");
        return null;
      }
      else {
        XMLGregorianCalendar startObj = null, endObj = null;
        int intervalMinutes = 0;
        // check if start timestamp is OK
        try {
          startObj = Tstamp.makeTimestamp(this.startTime);
        }
        catch (Exception e) {
          setStatusBadTimestamp(this.startTime);
          return null;
        }
        // check if end timestamp is OK
        try {
          endObj = Tstamp.makeTimestamp(this.endTime);
        }
        catch (Exception e) {
          setStatusBadTimestamp(this.endTime);
          return null;
        }

        if (this.interval != null) {
          // convert to integer
          try {
            intervalMinutes = Integer.valueOf(this.interval);
          }
          catch (NumberFormatException e) {
            setStatusBadSamplingInterval(this.interval);
          }
        }
        // build XML string
        try {
          xmlString = getEnergy(startObj, endObj, intervalMinutes);
          // if we get a null, then there is no SensorData for this range
          if (xmlString == null) {
            setStatusBadRange(startObj.toString(), endObj.toString());
            return null;
          }
          return super.getStringRepresentation(xmlString);
        }
        catch (JAXBException e) {
          setStatusInternalError(e);
          return null;
        }
      }
    }
    // Some MediaType other than text/xml requested
    else {
      return null;
    }
  }
}
