/**
 * ItemInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;


/**
 * attributes field value is  a combination of the following flags:
 * 1 - read only,
 *                 2 - archive,
 *                 4 - hidden,
 *                 8 - system,
 *                 16 - dont index
 */
public class ItemInfo  implements java.io.Serializable {
    private java.lang.String path;

    private oracle.bi.webservices.v6.ItemInfoType type;

    private java.lang.String caption;

    private java.lang.Integer attributes;

    private java.util.Calendar lastModified;

    private java.util.Calendar created;

    private java.util.Calendar accessed;

    private oracle.bi.webservices.v6.Account creator;

    private oracle.bi.webservices.v6.Account lastModifier;

    private java.lang.String signature;

    private java.lang.String targetPath;

    private oracle.bi.webservices.v6.ACL acl;

    private oracle.bi.webservices.v6.Account owner;

    private oracle.bi.webservices.v6.NameValuePair[] itemProperties;

    public ItemInfo() {
    }

    public ItemInfo(
           java.lang.String path,
           oracle.bi.webservices.v6.ItemInfoType type,
           java.lang.String caption,
           java.lang.Integer attributes,
           java.util.Calendar lastModified,
           java.util.Calendar created,
           java.util.Calendar accessed,
           oracle.bi.webservices.v6.Account creator,
           oracle.bi.webservices.v6.Account lastModifier,
           java.lang.String signature,
           java.lang.String targetPath,
           oracle.bi.webservices.v6.ACL acl,
           oracle.bi.webservices.v6.Account owner,
           oracle.bi.webservices.v6.NameValuePair[] itemProperties) {
           this.path = path;
           this.type = type;
           this.caption = caption;
           this.attributes = attributes;
           this.lastModified = lastModified;
           this.created = created;
           this.accessed = accessed;
           this.creator = creator;
           this.lastModifier = lastModifier;
           this.signature = signature;
           this.targetPath = targetPath;
           this.acl = acl;
           this.owner = owner;
           this.itemProperties = itemProperties;
    }


    /**
     * Gets the path value for this ItemInfo.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this ItemInfo.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the type value for this ItemInfo.
     * 
     * @return type
     */
    public oracle.bi.webservices.v6.ItemInfoType getType() {
        return type;
    }


    /**
     * Sets the type value for this ItemInfo.
     * 
     * @param type
     */
    public void setType(oracle.bi.webservices.v6.ItemInfoType type) {
        this.type = type;
    }


    /**
     * Gets the caption value for this ItemInfo.
     * 
     * @return caption
     */
    public java.lang.String getCaption() {
        return caption;
    }


    /**
     * Sets the caption value for this ItemInfo.
     * 
     * @param caption
     */
    public void setCaption(java.lang.String caption) {
        this.caption = caption;
    }


    /**
     * Gets the attributes value for this ItemInfo.
     * 
     * @return attributes
     */
    public java.lang.Integer getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this ItemInfo.
     * 
     * @param attributes
     */
    public void setAttributes(java.lang.Integer attributes) {
        this.attributes = attributes;
    }


    /**
     * Gets the lastModified value for this ItemInfo.
     * 
     * @return lastModified
     */
    public java.util.Calendar getLastModified() {
        return lastModified;
    }


    /**
     * Sets the lastModified value for this ItemInfo.
     * 
     * @param lastModified
     */
    public void setLastModified(java.util.Calendar lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * Gets the created value for this ItemInfo.
     * 
     * @return created
     */
    public java.util.Calendar getCreated() {
        return created;
    }


    /**
     * Sets the created value for this ItemInfo.
     * 
     * @param created
     */
    public void setCreated(java.util.Calendar created) {
        this.created = created;
    }


    /**
     * Gets the accessed value for this ItemInfo.
     * 
     * @return accessed
     */
    public java.util.Calendar getAccessed() {
        return accessed;
    }


    /**
     * Sets the accessed value for this ItemInfo.
     * 
     * @param accessed
     */
    public void setAccessed(java.util.Calendar accessed) {
        this.accessed = accessed;
    }


    /**
     * Gets the creator value for this ItemInfo.
     * 
     * @return creator
     */
    public oracle.bi.webservices.v6.Account getCreator() {
        return creator;
    }


    /**
     * Sets the creator value for this ItemInfo.
     * 
     * @param creator
     */
    public void setCreator(oracle.bi.webservices.v6.Account creator) {
        this.creator = creator;
    }


    /**
     * Gets the lastModifier value for this ItemInfo.
     * 
     * @return lastModifier
     */
    public oracle.bi.webservices.v6.Account getLastModifier() {
        return lastModifier;
    }


    /**
     * Sets the lastModifier value for this ItemInfo.
     * 
     * @param lastModifier
     */
    public void setLastModifier(oracle.bi.webservices.v6.Account lastModifier) {
        this.lastModifier = lastModifier;
    }


    /**
     * Gets the signature value for this ItemInfo.
     * 
     * @return signature
     */
    public java.lang.String getSignature() {
        return signature;
    }


    /**
     * Sets the signature value for this ItemInfo.
     * 
     * @param signature
     */
    public void setSignature(java.lang.String signature) {
        this.signature = signature;
    }


    /**
     * Gets the targetPath value for this ItemInfo.
     * 
     * @return targetPath
     */
    public java.lang.String getTargetPath() {
        return targetPath;
    }


    /**
     * Sets the targetPath value for this ItemInfo.
     * 
     * @param targetPath
     */
    public void setTargetPath(java.lang.String targetPath) {
        this.targetPath = targetPath;
    }


    /**
     * Gets the acl value for this ItemInfo.
     * 
     * @return acl
     */
    public oracle.bi.webservices.v6.ACL getAcl() {
        return acl;
    }


    /**
     * Sets the acl value for this ItemInfo.
     * 
     * @param acl
     */
    public void setAcl(oracle.bi.webservices.v6.ACL acl) {
        this.acl = acl;
    }


    /**
     * Gets the owner value for this ItemInfo.
     * 
     * @return owner
     */
    public oracle.bi.webservices.v6.Account getOwner() {
        return owner;
    }


    /**
     * Sets the owner value for this ItemInfo.
     * 
     * @param owner
     */
    public void setOwner(oracle.bi.webservices.v6.Account owner) {
        this.owner = owner;
    }


    /**
     * Gets the itemProperties value for this ItemInfo.
     * 
     * @return itemProperties
     */
    public oracle.bi.webservices.v6.NameValuePair[] getItemProperties() {
        return itemProperties;
    }


    /**
     * Sets the itemProperties value for this ItemInfo.
     * 
     * @param itemProperties
     */
    public void setItemProperties(oracle.bi.webservices.v6.NameValuePair[] itemProperties) {
        this.itemProperties = itemProperties;
    }

    public oracle.bi.webservices.v6.NameValuePair getItemProperties(int i) {
        return this.itemProperties[i];
    }

    public void setItemProperties(int i, oracle.bi.webservices.v6.NameValuePair _value) {
        this.itemProperties[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemInfo)) return false;
        ItemInfo other = (ItemInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.caption==null && other.getCaption()==null) || 
             (this.caption!=null &&
              this.caption.equals(other.getCaption()))) &&
            ((this.attributes==null && other.getAttributes()==null) || 
             (this.attributes!=null &&
              this.attributes.equals(other.getAttributes()))) &&
            ((this.lastModified==null && other.getLastModified()==null) || 
             (this.lastModified!=null &&
              this.lastModified.equals(other.getLastModified()))) &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.accessed==null && other.getAccessed()==null) || 
             (this.accessed!=null &&
              this.accessed.equals(other.getAccessed()))) &&
            ((this.creator==null && other.getCreator()==null) || 
             (this.creator!=null &&
              this.creator.equals(other.getCreator()))) &&
            ((this.lastModifier==null && other.getLastModifier()==null) || 
             (this.lastModifier!=null &&
              this.lastModifier.equals(other.getLastModifier()))) &&
            ((this.signature==null && other.getSignature()==null) || 
             (this.signature!=null &&
              this.signature.equals(other.getSignature()))) &&
            ((this.targetPath==null && other.getTargetPath()==null) || 
             (this.targetPath!=null &&
              this.targetPath.equals(other.getTargetPath()))) &&
            ((this.acl==null && other.getAcl()==null) || 
             (this.acl!=null &&
              this.acl.equals(other.getAcl()))) &&
            ((this.owner==null && other.getOwner()==null) || 
             (this.owner!=null &&
              this.owner.equals(other.getOwner()))) &&
            ((this.itemProperties==null && other.getItemProperties()==null) || 
             (this.itemProperties!=null &&
              java.util.Arrays.equals(this.itemProperties, other.getItemProperties())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getCaption() != null) {
            _hashCode += getCaption().hashCode();
        }
        if (getAttributes() != null) {
            _hashCode += getAttributes().hashCode();
        }
        if (getLastModified() != null) {
            _hashCode += getLastModified().hashCode();
        }
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        if (getAccessed() != null) {
            _hashCode += getAccessed().hashCode();
        }
        if (getCreator() != null) {
            _hashCode += getCreator().hashCode();
        }
        if (getLastModifier() != null) {
            _hashCode += getLastModifier().hashCode();
        }
        if (getSignature() != null) {
            _hashCode += getSignature().hashCode();
        }
        if (getTargetPath() != null) {
            _hashCode += getTargetPath().hashCode();
        }
        if (getAcl() != null) {
            _hashCode += getAcl().hashCode();
        }
        if (getOwner() != null) {
            _hashCode += getOwner().hashCode();
        }
        if (getItemProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItemProperties(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ItemInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("path");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caption");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "caption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModified");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "lastModified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessed");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "accessed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creator");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "creator"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifier");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "lastModifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signature");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "signature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetPath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "targetPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acl");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "acl"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ACL"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owner");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "owner"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemProperties");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "itemProperties"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
