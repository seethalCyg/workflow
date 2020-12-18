/**
 * ExecuteXMLQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class ExecuteXMLQuery  implements java.io.Serializable {
    private oracle.bi.webservices.v6.ReportRef report;

    private oracle.bi.webservices.v6.XMLQueryOutputFormat outputFormat;

    private oracle.bi.webservices.v6.XMLQueryExecutionOptions executionOptions;

    private oracle.bi.webservices.v6.ReportParams reportParams;

    private java.lang.String sessionID;

    public ExecuteXMLQuery() {
    }

    public ExecuteXMLQuery(
           oracle.bi.webservices.v6.ReportRef report,
           oracle.bi.webservices.v6.XMLQueryOutputFormat outputFormat,
           oracle.bi.webservices.v6.XMLQueryExecutionOptions executionOptions,
           oracle.bi.webservices.v6.ReportParams reportParams,
           java.lang.String sessionID) {
           this.report = report;
           this.outputFormat = outputFormat;
           this.executionOptions = executionOptions;
           this.reportParams = reportParams;
           this.sessionID = sessionID;
    }


    /**
     * Gets the report value for this ExecuteXMLQuery.
     * 
     * @return report
     */
    public oracle.bi.webservices.v6.ReportRef getReport() {
        return report;
    }


    /**
     * Sets the report value for this ExecuteXMLQuery.
     * 
     * @param report
     */
    public void setReport(oracle.bi.webservices.v6.ReportRef report) {
        this.report = report;
    }


    /**
     * Gets the outputFormat value for this ExecuteXMLQuery.
     * 
     * @return outputFormat
     */
    public oracle.bi.webservices.v6.XMLQueryOutputFormat getOutputFormat() {
        return outputFormat;
    }


    /**
     * Sets the outputFormat value for this ExecuteXMLQuery.
     * 
     * @param outputFormat
     */
    public void setOutputFormat(oracle.bi.webservices.v6.XMLQueryOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }


    /**
     * Gets the executionOptions value for this ExecuteXMLQuery.
     * 
     * @return executionOptions
     */
    public oracle.bi.webservices.v6.XMLQueryExecutionOptions getExecutionOptions() {
        return executionOptions;
    }


    /**
     * Sets the executionOptions value for this ExecuteXMLQuery.
     * 
     * @param executionOptions
     */
    public void setExecutionOptions(oracle.bi.webservices.v6.XMLQueryExecutionOptions executionOptions) {
        this.executionOptions = executionOptions;
    }


    /**
     * Gets the reportParams value for this ExecuteXMLQuery.
     * 
     * @return reportParams
     */
    public oracle.bi.webservices.v6.ReportParams getReportParams() {
        return reportParams;
    }


    /**
     * Sets the reportParams value for this ExecuteXMLQuery.
     * 
     * @param reportParams
     */
    public void setReportParams(oracle.bi.webservices.v6.ReportParams reportParams) {
        this.reportParams = reportParams;
    }


    /**
     * Gets the sessionID value for this ExecuteXMLQuery.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this ExecuteXMLQuery.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExecuteXMLQuery)) return false;
        ExecuteXMLQuery other = (ExecuteXMLQuery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.report==null && other.getReport()==null) || 
             (this.report!=null &&
              this.report.equals(other.getReport()))) &&
            ((this.outputFormat==null && other.getOutputFormat()==null) || 
             (this.outputFormat!=null &&
              this.outputFormat.equals(other.getOutputFormat()))) &&
            ((this.executionOptions==null && other.getExecutionOptions()==null) || 
             (this.executionOptions!=null &&
              this.executionOptions.equals(other.getExecutionOptions()))) &&
            ((this.reportParams==null && other.getReportParams()==null) || 
             (this.reportParams!=null &&
              this.reportParams.equals(other.getReportParams()))) &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID())));
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
        if (getReport() != null) {
            _hashCode += getReport().hashCode();
        }
        if (getOutputFormat() != null) {
            _hashCode += getOutputFormat().hashCode();
        }
        if (getExecutionOptions() != null) {
            _hashCode += getExecutionOptions().hashCode();
        }
        if (getReportParams() != null) {
            _hashCode += getReportParams().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExecuteXMLQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeXMLQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "report"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportRef"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputFormat");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "outputFormat"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "XMLQueryOutputFormat"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executionOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "executionOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "XMLQueryExecutionOptions"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportParams"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
