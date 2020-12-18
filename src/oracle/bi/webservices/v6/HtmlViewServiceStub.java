/**
 * HtmlViewServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class HtmlViewServiceStub extends org.apache.axis.client.Stub implements oracle.bi.webservices.v6.HtmlViewServiceSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[8];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("startPage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "StartPageParams"), oracle.bi.webservices.v6.StartPageParams.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("endPage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addReportToPage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "report"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportRef"), oracle.bi.webservices.v6.ReportRef.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportViewName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportParams"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportParams"), oracle.bi.webservices.v6.ReportParams.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "options"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportHTMLOptions"), oracle.bi.webservices.v6.ReportHTMLOptions.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHeadersHtml");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCommonBodyHtml");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHtmlForReport");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageReportID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setBridge");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "bridge"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getHtmlForPageWithOneReport");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "report"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportRef"), oracle.bi.webservices.v6.ReportRef.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportViewName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportParams"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportParams"), oracle.bi.webservices.v6.ReportParams.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportOptions"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportHTMLOptions"), oracle.bi.webservices.v6.ReportHTMLOptions.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageParams"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "StartPageParams"), oracle.bi.webservices.v6.StartPageParams.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

    }

    public HtmlViewServiceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public HtmlViewServiceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public HtmlViewServiceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
        addBindings2();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">addReportToPage");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.AddReportToPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">addReportToPageResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.AddReportToPageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">applyReportDefaults");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ApplyReportDefaults.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">applyReportDefaultsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ApplyReportDefaultsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">applyReportParams");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ApplyReportParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">applyReportParamsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ApplyReportParamsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">cancelJob");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CancelJob.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">cancelJobResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CancelJobResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">cancelQuery");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CancelQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">cancelQueryResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CancelQueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">clearQueryCache");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ClearQueryCache.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">clearQueryCacheResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ClearQueryCacheResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">copyItem");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CopyItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">copyItem2");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CopyItem2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">copyItem2Result");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CopyItem2Result.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">copyItemResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CopyItemResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">createFolder");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CreateFolder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">createFolderResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CreateFolderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">createLink");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CreateLink.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">createLinkResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CreateLinkResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">deleteIBot");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DeleteIBot.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">deleteIBotResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DeleteIBotResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">deleteItem");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DeleteItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">deleteItemResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DeleteItemResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">deleteResultSet");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DeleteResultSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">deleteResultSetResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DeleteResultSetResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeColumn");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DescribeColumn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeColumnResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DescribeColumnResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeSubjectArea");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DescribeSubjectArea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeSubjectAreaResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DescribeSubjectAreaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeTable");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DescribeTable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeTableResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.DescribeTableResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">endPage");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.EndPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">endPageResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.EndPageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">evaluateCondition");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.EvaluateCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">evaluateConditionResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.EvaluateConditionResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">evaluateInlineCondition");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.EvaluateInlineCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">evaluateInlineConditionResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.EvaluateInlineConditionResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeIBotNow");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExecuteIBotNow.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeIBotNowResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExecuteIBotNowResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeSQLQuery");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExecuteSQLQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeSQLQueryResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExecuteSQLQueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeXMLQuery");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExecuteXMLQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">executeXMLQueryResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExecuteXMLQueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">export");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Export.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">exportResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExportResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">fetchNext");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.FetchNext.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">fetchNextResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.FetchNextResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">forgetAccounts");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ForgetAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">forgetAccountsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ForgetAccountsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">generateReportSQL");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GenerateReportSQL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">generateReportSQLResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GenerateReportSQLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getAccounts");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getAccountsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Account[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "accountDetails");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getCommonBodyHtml");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetCommonBodyHtml.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getCommonBodyHtmlResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetCommonBodyHtmlResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getConditionCustomizableReportElements");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetConditionCustomizableReportElements.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getConditionCustomizableReportElementsResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "customizableElement");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getCounts");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetCounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getCountsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetCountsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getCurUser");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetCurUser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getCurUserResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetCurUserResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getGlobalPrivilegeACL");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetGlobalPrivilegeACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getGlobalPrivilegeACLResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetGlobalPrivilegeACLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getGlobalPrivileges");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetGlobalPrivileges.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getGlobalPrivilegesResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Privilege[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Privilege");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sawPrivileges");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getGroups");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getGroupsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Account[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "account");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHeadersHtml");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetHeadersHtml.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHeadersHtmlResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetHeadersHtmlResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHtmlForPageWithOneReport");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetHtmlForPageWithOneReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHtmlForPageWithOneReportResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetHtmlForPageWithOneReportResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHtmlForReport");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetHtmlForReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHtmlForReportResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetHtmlForReportResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getItemInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetItemInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getItemInfoResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetItemInfoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getJobInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetJobInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getJobInfoResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetJobInfoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getMembers");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetMembers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getMembersResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Account[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "account");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPermissions");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetPermissions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPermissionsResult");
            cachedSerQNames.add(qName);
            cls = int[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPrivilegesStatus");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetPrivilegesStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPrivilegesStatusResult");
            cachedSerQNames.add(qName);
            cls = boolean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPromptedColumns");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetPromptedColumns.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPromptedColumnsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetPromptedColumnsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPromptedFilters");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetPromptedFilters.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getPromptedFiltersResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "promptedFilter");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSessionEnvironment");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSessionEnvironment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSessionEnvironmentResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSessionEnvironmentResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSessionVariables");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSessionVariables.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSessionVariablesResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSubItems");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSubItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSubItemsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ItemInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfo");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "itemInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSubjectAreas");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSubjectAreas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getSubjectAreasResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SASubjectArea[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SASubjectArea");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "subjectArea");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">impersonate");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Impersonate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">impersonateex");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Impersonateex.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">impersonateexResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ImpersonateexResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">impersonateResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ImpersonateResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">import");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6._import.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">importResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ImportError[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ImportError");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "error");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">isMember");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.IsMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">isMemberResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.IsMemberResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">JobInfo>detailedInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.JobInfoDetailedInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">JobStats>jobState");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.JobStatsJobState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">joinGroups");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.JoinGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">joinGroupsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.JoinGroupsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">keepAlive");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">keepAliveResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.KeepAliveResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">leaveGroups");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.LeaveGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">leaveGroupsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.LeaveGroupsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">logoff");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Logoff.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">logoffResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.LogoffResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">logon");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Logon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">logonex");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Logonex.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">logonexResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.LogonexResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">logonResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.LogonResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">maintenanceMode");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MaintenanceMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">maintenanceModeResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MaintenanceModeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">markForReplication");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MarkForReplication.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">markForReplicationResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MarkForReplicationResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">moveIBot");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MoveIBot.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">moveIBotResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MoveIBotResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">moveItem");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MoveItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">moveItemResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.MoveItemResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">pasteItem2");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PasteItem2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">pasteItem2Result");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PasteItem2Result.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">prepareCache");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PrepareCache.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">prepareCacheResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PrepareCacheResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">purgeCache");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PurgeCache.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">purgeCacheResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PurgeCacheResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">purgeLog");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PurgeLog.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">purgeLogResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PurgeLogResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">readObjects");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ReadObjects.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">readObjectsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CatalogObject[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "CatalogObject");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "catalogObject");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">removeFolder");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.RemoveFolder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">removeFolderResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.RemoveFolderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">renameAccounts");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.RenameAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">renameAccountsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.RenameAccountsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">saveResultSet");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SaveResultSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">saveResultSetResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SaveResultSetResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">sendMessage");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SendMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">sendMessageResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SendMessageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setBridge");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetBridge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setBridgeResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetBridgeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setItemAttributes");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetItemAttributes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setItemAttributesResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetItemAttributesResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setItemProperty");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetItemProperty.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setItemPropertyResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetItemPropertyResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setOwnership");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetOwnership.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">setOwnershipResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SetOwnershipResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">startPage");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.StartPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">startPageResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.StartPageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">subscribe");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Subscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">subscribeResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SubscribeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">unsubscribe");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Unsubscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">unsubscribeResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UnsubscribeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">updateCatalogItemACL");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateCatalogItemACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">updateCatalogItemACLResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateCatalogItemACLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">updateGlobalPrivilegeACL");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateGlobalPrivilegeACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">updateGlobalPrivilegeACLResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateGlobalPrivilegeACLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">upgradeXML");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpgradeXML.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">upgradeXMLResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpgradeXMLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">voidType");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.VoidType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">writeIBot");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.WriteIBot.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">writeIBotResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.WriteIBotResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">writeListFiles");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.WriteListFiles.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">writeListFilesResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.WriteListFilesResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">writeObjects");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.WriteObjects.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">writeObjectsResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ErrorInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ErrorInfo");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "errorInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "AccessControlToken");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.AccessControlToken.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Account");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Account.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ACL");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "AggregationRule");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.AggregationRule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "arrayOfGUIDs");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "GUID");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "AuthResult");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.AuthResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "CatalogItemsFilter");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CatalogItemsFilter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "CatalogObject");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.CatalogObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ErrorDetailsLevel");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ErrorDetailsLevel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ErrorInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ErrorInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ExportFlags");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ExportFlags.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "FileInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.FileInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "GetSubItemsFilter");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSubItemsFilter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "GetSubItemsParams");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.GetSubItemsParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ImportError");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ImportError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ImportFlags");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ImportFlags.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ItemInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfoType");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ItemInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "JobInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.JobInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "JobStats");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.JobStats.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "LogonParameter");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.LogonParameter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "NameValueArrayPair");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.NameValueArrayPair.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "NameValuePair");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.NameValuePair.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "OverrideType");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.OverrideType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "PathMap");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PathMapEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "PathMapEntry");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pathMapEntries");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "PathMapEntry");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.PathMapEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Privilege");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Privilege.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "PromptedColumnInfo");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Columns");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "QueryResults");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.QueryResults.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings2() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReadObjectsReturnOptions");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ReadObjectsReturnOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportHTMLLinksMode");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ReportHTMLLinksMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportHTMLOptions");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ReportHTMLOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportParams");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ReportParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportRef");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.ReportRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SAColumn");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SAColumn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SADataType");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SADataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SASubjectArea");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SASubjectArea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SASubjectAreaDetails");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SASubjectAreaDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SATable");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SATable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SATableDetails");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SATableDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SAWException");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SAWException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SAWLocale");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SAWLocale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SAWSessionParameters");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SAWSessionParameters.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SegmentationOptions");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SegmentationOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SessionEnvironment");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.SessionEnvironment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "StartPageParams");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.StartPageParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "TemplateInfo");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.TemplateInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "TemplateInfoInstance");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.TemplateInfoInstance.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "TreeNodePath");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.TreeNodePath.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "UpdateACLMode");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateACLMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "UpdateACLParams");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateACLParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "UpdateCatalogItemACLParams");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.UpdateCatalogItemACLParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "Variable");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.Variable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "XMLQueryExecutionOptions");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.XMLQueryExecutionOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "XMLQueryOutputFormat");
            cachedSerQNames.add(qName);
            cls = oracle.bi.webservices.v6.XMLQueryOutputFormat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public java.lang.String startPage(oracle.bi.webservices.v6.StartPageParams options, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#startPage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "startPage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {options, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void endPage(java.lang.String pageID, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#endPage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "endPage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pageID, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void addReportToPage(java.lang.String pageID, java.lang.String reportID, oracle.bi.webservices.v6.ReportRef report, java.lang.String reportViewName, oracle.bi.webservices.v6.ReportParams reportParams, oracle.bi.webservices.v6.ReportHTMLOptions options, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#addReportToPage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "addReportToPage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pageID, reportID, report, reportViewName, reportParams, options, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getHeadersHtml(java.lang.String pageID, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getHeadersHtml");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "getHeadersHtml"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pageID, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCommonBodyHtml(java.lang.String pageID, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getCommonBodyHtml");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "getCommonBodyHtml"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pageID, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getHtmlForReport(java.lang.String pageID, java.lang.String pageReportID, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getHtmlForReport");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "getHtmlForReport"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pageID, pageReportID, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void setBridge(java.lang.String bridge, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#setBridge");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "setBridge"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bridge, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getHtmlForPageWithOneReport(java.lang.String reportID, oracle.bi.webservices.v6.ReportRef report, java.lang.String reportViewName, oracle.bi.webservices.v6.ReportParams reportParams, oracle.bi.webservices.v6.ReportHTMLOptions reportOptions, oracle.bi.webservices.v6.StartPageParams pageParams, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getHtmlForPageWithOneReport");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "getHtmlForPageWithOneReport"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {reportID, report, reportViewName, reportParams, reportOptions, pageParams, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
