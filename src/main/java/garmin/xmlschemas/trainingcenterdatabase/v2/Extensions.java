//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.22 at 04:31:13 PM MDT 
//

package garmin.xmlschemas.trainingcenterdatabase.v2;

import garmin.xmlschemas.activityextension.v2.LX;
import garmin.xmlschemas.activityextension.v2.TPX;
import garmin.xmlschemas.trainingcenterdatabase.v2.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;





/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://www.garmin.com/xmlschemas/ActivityExtension/v2}TPX"/>
 *         &lt;element ref="{http://www.garmin.com/xmlschemas/ActivityExtension/v2}LX"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tpx",
    "lx"
})
@XmlRootElement(name = "Extensions")
public class Extensions {

    @XmlElement(name = "TPX", namespace = "http://www.garmin.com/xmlschemas/ActivityExtension/v2")
    protected TPX tpx;
    @XmlElement(name = "LX", namespace = "http://www.garmin.com/xmlschemas/ActivityExtension/v2")
    protected LX lx;

    /**
     * Gets the value of the tpx property.
     * 
     * @return
     *     possible object is
     *     {@link TPX }
     *     
     */
    public TPX getTPX() {
        return tpx;
    }

    /**
     * Sets the value of the tpx property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPX }
     *     
     */
    public void setTPX(TPX value) {
        this.tpx = value;
    }

    /**
     * Gets the value of the lx property.
     * 
     * @return
     *     possible object is
     *     {@link LX }
     *     
     */
    public LX getLX() {
        return lx;
    }

    /**
     * Sets the value of the lx property.
     * 
     * @param value
     *     allowed object is
     *     {@link LX }
     *     
     */
    public void setLX(LX value) {
        this.lx = value;
    }

}