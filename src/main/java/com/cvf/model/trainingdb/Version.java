//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.09 at 12:20:23 PM MDT 
//


package com.cvf.model.trainingdb;

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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}VersionMajor"/>
 *         &lt;element ref="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}VersionMinor"/>
 *         &lt;element ref="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}BuildMajor"/>
 *         &lt;element ref="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}BuildMinor"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "versionMajor",
    "versionMinor",
    "buildMajor",
    "buildMinor"
})
@XmlRootElement(name = "Version")
public class Version {

    @XmlElement(name = "VersionMajor")
    protected byte versionMajor;
    @XmlElement(name = "VersionMinor")
    protected byte versionMinor;
    @XmlElement(name = "BuildMajor", required = true)
    protected String buildMajor;
    @XmlElement(name = "BuildMinor", required = true)
    protected String buildMinor;

    /**
     * Gets the value of the versionMajor property.
     * 
     */
    public byte getVersionMajor() {
        return versionMajor;
    }

    /**
     * Sets the value of the versionMajor property.
     * 
     */
    public void setVersionMajor(byte value) {
        this.versionMajor = value;
    }

    /**
     * Gets the value of the versionMinor property.
     * 
     */
    public byte getVersionMinor() {
        return versionMinor;
    }

    /**
     * Sets the value of the versionMinor property.
     * 
     */
    public void setVersionMinor(byte value) {
        this.versionMinor = value;
    }

    /**
     * Gets the value of the buildMajor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildMajor() {
        return buildMajor;
    }

    /**
     * Sets the value of the buildMajor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildMajor(String value) {
        this.buildMajor = value;
    }

    /**
     * Gets the value of the buildMinor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildMinor() {
        return buildMinor;
    }

    /**
     * Sets the value of the buildMinor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildMinor(String value) {
        this.buildMinor = value;
    }

}