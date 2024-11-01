package com.example.car_showroom.constant;

public class ApplicationConstants {
    public static final String HEADER_LANGUAGE = "accepted-language";
    public static final String ENGLISH = "en";
    public static final String ARABIC = "ar";
    public static final String DEFAULT_LANGUAGE = ENGLISH;
    public static final String LANGUAGE = "language";
    public static final String CURRENCY_SAR = "SAR";
    public static final String NAME_CHANNELS = "Channels by STC";
    public static final String DEFAULT_CURRENCY = CURRENCY_SAR;
    public static final String DEFAULT_COUNTRY_CODE = "SA";
    public static final String CRN = "CRN";
    public static final String DEFAULT_TIME_ZONE = "Asia/Riyadh";

    public static final String CHANNELS_BY_STC_VAT = "300095781100003";
    public static final String STC_GROUP_VAT = "300000157210003";

    //Channels address
    public static final String DEFAULT_SELLER_STREET = "Olya street";
    public static final String DEFAULT_SELLER_BUILDING_NUM = "6547";
    public static final String DEFAULT_SELLER_ADDITIONAL_NUM = "2653";
    public static final String DEFAULT_SELLER_CITY = "Riyadh";
    public static final String DEFAULT_SELLER_POSTAL_CODE = "11622";
    public static final String DEFAULT_SELLER_DISTRICT = "Al Muruj";
    public static final String DEFAULT_OTHER_SELLER_ID = "1010242577";
    public static final String DEFAULT_OTHER_SELLER_SCHEMA_ID = CRN;
    public static final String DEFAULT_SELLER_NAME = NAME_CHANNELS;
    //XML default values
    public static final String TAX_INVOICE_CODE ="388";
    public static final String CREDIT_NOTE_CODE ="381";
    public static final String DEBIT_NOTE_CODE ="383";
    public static final String CREDIT_TITLE = "إشعار دائن";
    public static final String DEBIT_TITLE = "إشعارمدين";
    public static final String DEFAULT_TYPE_CODE = TAX_INVOICE_CODE;


    public static final String XML_INVOICE_TYPE_STANDARD = "0100000";
    public static final String XML_INVOICE_TYPE_SIMPLIFIED = "0200000";
    public static final String VAT_CATEGORY_CODE_S = "S";
    public static final String VAT_CATEGORY_CODE_O = "O";
    public static final String VAT_CATEGORY_CODE_E = "E";
    public static final String VAT_CATEGORY_CODE_Z = "Z";

    //regex Expressions
    public static final String VAT_CATEGORY_REGEX = "^S$|^O$|^Z$|^E$";
    public static final String INVOICE_TYPE_CODE_REGEX = "^TaxInvoice$|^CreditNote$|^DebitNote$";
    public static final String EXACTLY_10_DIGITS_REGEX = "^[0-9]{10}$";
    public static final String MAX_15_DIGITS_REGEX = "^[0-9]{1,15}$";
    public static final String VAT_NUMBER_REGEX = "^[3]{1}[0-9]{13}[3]{1}$";
    public static final String YYYY_MM_DD_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    public static final String HH_MM_SS_REGEX = "^([0-2]:\\d|[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
    public static final String TWO_LETTERS_REGEX = "^[A-Za-z]{2}$";



    // exemption reasons code
    public static final String EXEMPTION_VATEX_SA_EDU = "VATEX-SA-EDU";
    public static final String EXEMPTION_VATEX_SA_HEA = "VATEX-SA-HEA";

    //Qr
    public static final String ZATCA_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // previous invoice hash for first invoice
    public static final String PIH = "NWZlY2ViNjZmZmM4NmYzOGQ5NTI3ODZjNmQ2OTZjNzljMmRiYzIzOWRkNGU5MWI0NjcyOWQ3M2EyN2ZiNTdlOQ==";

    //Jasper constants
    public static final String STANDARD_INVOICE_JASPER_TEMPLATE_PATH = "/templates/STDInvoice.jrxml";
    public static final String STANDARD_DEBIT_CREDIT_INVOICE_JASPER_TEMPLATE_PATH = "/templates/STD_Debit_Credit.jrxml";
    public static final String SIMPLIFIED_INVOICE_JASPER_TEMPLATE_PATH = "/templates/SIMInvoice.jrxml";
    public static final String SIMPLIFIED_DEBIT_CREDIT_INVOICE_JASPER_TEMPLATE_PATH = "/templates/SIM_Debit_Credit.jrxml";
    public static final String Jasper_INVOICE_LOGO = "invoiceLogo";
    public static final String JASPER_INVOICE_NUMBER = "invoiceNumber";
    public static final String JASPER_APPLICATION_NAME = "applicationName";
    public static final String JASPER_INVOICE_DATE = "invoiceDate";
    public static final String JASPER_PRINT_TIME = "printTime";
    public static final String JASPER_BUYER_TIN = "buyerTin";
    public static final String JASPER_BUYER_NAME = "buyerName";
    public static final String JASPER_BUYER_ID = "buyerID";
    public static final String JASPER_OTHER_BUYER_SCHEMA_ID = "otherBuyerSchemaId";
    public static final String JASPER_BUYER_ADDRESS = "buyerAddress";
    public static final String JASPER_SELLER_NAME = "sellerName";
    public static final String JASPER_SELLER_TIN = "sellerTin";
    public static final String JASPER_SELLER_ID = "sellerID";
    public static final String JASPER_OTHER_SELLER_SCHEMA_ID = "otherSellerSchemaId";
    public static final String JASPER_TOTAL_WITHOUT_VAT = "totalWithoutVat";
    public static final String JASPER_TOTAL_DISCOUNTS = "totalDiscounts";
    public static final String JASPER_SUM_OF_ALLOWANCE_DOC_LEVEL = "sumOfAllowancesOnDocumentLevel";
    public static final String JASPER_TOTAL_VAT = "totalVat";
    public static final String JASPER_TOTAL_WITH_VAT = "totalWithVat";
    public static final String JASPER_QR_SHOW = "showQRCode";
    public static final String JASPER_QR_CODE = "qrLogo";
    public static final String JASPER_INVOICE_CURRENCY = "invoiceCurrency";
    public static final String JASPER_DEFAULT_CURRENCY = "SAR - ريال";
    public static final String JASPER_STORENAME = "storeName";
    public static final String JASPER_TYPE_EN = "typeEn";
    public static final String TYPE_CODE = "typeCode";
    public static final String DEBIT_NOTE = "Debit";
    public static final String CREDIT_NOTE = "Credit";
    public static final String JASPER_NOTE_REFERENCE_ID = "noteRefNumber";
    public static final String CHANNELS_STC_LOG_PNG = "channels_stc_pos.png";

    //error codes
    public static final String QI_0000 = "QI-0000";
    public static final String QI_0001 = "QI-0001";
    public static final String QI_0002 = "QI-0002";
    public static final String QI_0003 = "QI-0003";
    public static final String QI_0004 = "QI-0004";
    public static final String QI_0005 = "QI-0005";
    public static final String QI_0006 = "QI-0006";
    public static final String QI_0007 = "QI-0007";
    public static final String QI_0008 = "QI-0008";
    public static final String QI_0009 = "QI-0009";
    public static final String QI_0010 = "QI-0010"; //this error code is when error occurs while trying to save a new record in db
    public static final String RESPONSE_TYPE_STATUS = "status";
    public static final String RESPONSE_TYPE_INVOICE = "invoice";
    public static final String UTC_3 = "UTC+3";
    public static final String TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String DEFAULT_TIME_FORMAT_2 = "yyyy-MM-dd HH:mm:sss";
    public static final String ZATCA_FILE_DATE_TIME_FORMAT = "yyyyMMdd'T'HHmmss";
    public static final String SERVER_IP_HEADER = "server-ip";
    public static final String MICRO_SERVICE_NAME = "invoice-services";
    public static final String ZATCA = "ZATCA";
    public static final String FORWARD_SLASH = "/";
    public static final String MODIFIED_DATA = "modifiedData";
    public static final String ROLE = "role";
    public static final String REQUEST_TYPE = "requestType";
    public static final String RESPONSE_TYPE = "responseType";
    public static final String UTC = "UTC";
    public static final String KSA_TIME_ZONE = "Asia/Riyadh";
    public static final Integer STANDARD_INDEX = 0;
    public static final Integer SIMPLIFIED_INDEX = 1;
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String ZATCA_PASS = "PASS";
    public static final String ZATCA_WARNING = "WARNING";
    public static final String ZATCA_ERROR = "ERROR";
    // http status codes
    public static final Integer STATUS_CODE_200 = 200;
    public static final Integer STATUS_CODE_202 = 202;
    public static final Integer STATUS_CODE_400 = 400;
    public static final Integer STATUS_CODE_401 = 401;
    public static final Integer STATUS_CODE_406 = 406;
    public static final Integer STATUS_CODE_500 = 500;
    public static final String AES = "AES";

    public static final String UTF_8 = "UTF-8";
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String OTHER_BUYER_ID_OTH = "OTH";
    public static final String YES_FLAG = "Y";
    public static final String NO_FLAG = "N";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String PAGE_LIMIT = "pageLimit";
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_LIMIT = "10";

    public static final String STRING_PERCENTAGE = "%";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_TYPE = "sortType";
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "sortDesc";


    //filters
    public static final String FILTER_NAME = "name";
    public static final String FILTER_CRN = "commercialRegistrationNumber";
    public static final String FILTER_ADDRESS = "address";
    public static final String FILTER_MANAGER_NAME = "managerName";
    public static final String FILTER_CONTACT_NUMBER = "contactNumber";



}
