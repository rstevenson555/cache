CREATE TABLE AUTHENTICATION
(
  USER_KEY                       varchar(17 ) NOT NULL,
  SUPLR_ID                       numeric(10)     NOT NULL,
  ACCOUNT_GROUP                  varchar(6 ) NOT NULL,
  PASSWORD                       varchar(25 ) NOT NULL,
  LOGIN_TYPE                     varchar(1 ) DEFAULT 'C' NOT NULL,
  LOGIN_STATE                    varchar(1 ) DEFAULT 'N' NOT NULL,
  PERMISSION_GRP_ID              numeric(15),
  APPROVAL_GRP_ID                numeric(15),
  USER_ACCT_GRP_ID               numeric(15),
  CREDIT_GRP_ID                  numeric(15),
  SUB_SUPLR_GRP_ID               numeric(10),
  NEW_MSG                        varchar(1 ) DEFAULT 'N',
  LAST_LOGIN_DATE                DATE,
  ALLOW_MULTIPLE_LOGINS          varchar(1 ) DEFAULT 'N' NOT NULL,
  ALLOW_OBI                      varchar(1 ) DEFAULT 'N' NOT NULL,
  START_PAGE_ID                  numeric         DEFAULT 1,
  DEFAULT_CATALOG                varchar(1 ),
  PASSWORD_CHANGE_DATE           DATE,
  LAST_MOD_DATE                  DATE,
  LAST_MOD_BY                    varchar(17 ),
  LOGIN_ID                       varchar(17 ) NOT NULL,
  ALLOW_ORDERPOINT               varchar(1 ) DEFAULT 'N' NOT NULL,
  CREATION_DATE                  DATE           DEFAULT sysdate,
  DEFAULT_CATALOG_TYPE_ID        numeric(1),
  CAN_USE_EMAIL_ACK              varchar(1 ),
  CONTACT_ID                     varchar(10 ),
  PIN                            varchar(10 ),
  PIN_ASSIGNMENT_STATE           varchar(1 ),
  NEED_HOST_SYNC                 varchar(1 ),
  THIRD_PARTY_EMAIL              varchar(80 ),
  THIRD_PARTY_OWNER              varchar(17 ),
  COPYMAX_USER_LEVEL             varchar(1 ) DEFAULT 'N' NOT NULL,
  ONLINE_REPORTING_STATUS        varchar(1 ) DEFAULT 'I' NOT NULL,
  ONLINE_REPORTING_TOKEN         varchar(60 ),
  SHOW_THUMBS                    varchar(1 ) DEFAULT 'Y' NOT NULL,
  SHOW_PRINT_LAUNCH_PAGE         varchar(1 ),
  PRINT_PREF_GRP_ID              numeric(15),
  ONLINE_SPEND_REPORTING_STATUS  varchar(1 ) DEFAULT 'I',
  ONLINE_SPEND_REPORTING_TOKEN   varchar(60 ),
  LOCK_DATE                      DATE,
  LOCK_CTR                       INTEGER        DEFAULT 0,
  NOWPRINT_SYNC_DATE             DATE
);

CREATE TABLE INFO
(
  USER_KEY             varchar(17 )        NOT NULL,
  ACCOUNT_NUMBER       varchar(30 ),
  MR_MS                varchar(8 )         NOT NULL,
  FIRST_NAME           varchar(30 )        NOT NULL,
  MID_INITIAL          varchar(1 ),
  LAST_NAME            varchar(30 )        NOT NULL,
  COMPANY_NAME         varchar(50 ),
  ADDRESS1             varchar(50 ),
  ADDRESS2             varchar(50 ),
  CITY                 varchar(30 ),
  STATE                varchar(30 ),
  ZIP                  varchar(10 ),
  ZIP_EXT              varchar(20 ),
  VOICE_COUNTRY_CODE   varchar(7 ),
  VOICE_AREA           varchar(10 )        NOT NULL,
  VOICE_PHONE          varchar(15 )        NOT NULL,
  VOICE_EXT            varchar(8 ),
  FAX_COUNTRY_CODE     varchar(7 ),
  FAX_AREA             varchar(10 ),
  FAX_PHONE            varchar(15 ),
  DATE_REGISTERED      DATE                     NOT NULL,
  EMAIL                varchar(80 ),
  DEPT                 varchar(30 ),
  TITLE                varchar(30 ),
  ACTIVATED_BY         varchar(17 ),
  DATE_ACTIVATED       DATE,
  COUNTRY_ID           numeric,
  USER_KEY_NAME        varchar(8 )         NOT NULL,
  SVGA                 varchar(1 ),
  BROWSER_NAME         varchar(8 ),
  BROWSER_VERS         varchar(8 ),
  SHOW_CONSIGNEE_DESC  varchar(1 ),
  TITLE_NUMBER         numeric,
  ALLOW_PROMO          varchar(1 ),
  CREATION_DATE        DATE                     DEFAULT SYSDATE
);

CREATE TABLE COMPANY_LIST
(
  COMPANY_ID     numeric                         NOT NULL,
  SUPLR_ID       numeric,
  COMPANY_NAME   varchar(50 )              NOT NULL,
  COMPANY_STATE  varchar(1 )               DEFAULT 'A'                   NOT NULL,
  LOGO_FILENAME  varchar(12 ),
  ADDRESS1       varchar(50 ),
  ADDRESS2       varchar(50 ),
  CITY           varchar(30 ),
  STATE          varchar(30 ),
  COUNTRY_ID     numeric,
  ZIP            varchar(10 ),
  ZIP_EXT        varchar(20 ),
  ORIGINATOR     varchar(17 )              NOT NULL,
  LAST_MOD_BY    varchar(17 ),
  LAST_MOD_DATE  DATE                           NOT NULL,
  CREATION_DATE  DATE                           DEFAULT sysdate
);

CREATE TABLE ACCOUNT_NUMBER_LIST
(
  ACCOUNT_NUMBER          varchar(30 )     NOT NULL,
  SUB_SUPLR_ID            numeric                NOT NULL,
  COMPANY_ID              numeric,
  ACCOUNT_NUMBER_STATE    varchar(1 )      DEFAULT 'A'                   NOT NULL,
  COST_CENTER_TYPE        varchar(1 ),
  ACCOUNT_NAME            varchar(50 ),
  ONE_CC_PER_ORDER        varchar(1 )      DEFAULT 'N',
  ACCOUNT_STATUS          varchar(1 ),
  USE_CONSIGNEE           varchar(1 )      DEFAULT 'N',
  LOCATION_CODE           varchar(3 ),
  ACCOUNT_TYPE            varchar(1 ),
  GSA_ACCOUNT             varchar(1 )      DEFAULT 'N',
  LAST_MOD_BY             varchar(17 ),
  LAST_MOD_DATE           DATE,
  ACCOUNT_LOAD_STATUS     varchar(1 )      DEFAULT 'N'                   NOT NULL,
  ACCOUNT_LOAD_DATE       DATE,
  SYNC_ACCOUNT_INFO       varchar(1 )      DEFAULT 'N'                   NOT NULL,
  CONTRACT_PROCESS        varchar(1 )      DEFAULT 'N',
  CREATION_DATE           DATE                  DEFAULT sysdate,
  USE_ALLOCATION_LIST     varchar(1 ),
  LIMIT_COST_CENTERS      varchar(1 ),
  DEFAULT_COST_CENTER     varchar(24 ),
  BYPASS_DEFAULT_CATALOG  varchar(1 )      DEFAULT 'N',
  TIER_ONE_ID             varchar(6 )      DEFAULT 'XX',
  NO_SPECIAL_ORDERS       varchar(1 )      DEFAULT 'N'                   NOT NULL,
  DEPARTMENT_CODE         varchar(2 )
);

CREATE TABLE ACCOUNT_GROUP
(
  ACCOUNT_GROUP            varchar(6 )     NOT NULL,
  ACCOUNT_GROUP_STATE      varchar(1 )     NOT NULL,
  COMPANY_ID               numeric,
  SUPLR_ID                 numeric,
  ACCOUNT_GROUP_NAME       varchar(50 )    NOT NULL,
  ORIGINATOR               varchar(17 )    NOT NULL,
  ACCOUNT_GROUP_TYPE       varchar(1 )     NOT NULL,
  LOGO_FILENAME            varchar(12 ),
  USE_CUSTOM_FIELDNAMES    varchar(1 ),
  ADDRESS1                 varchar(50 ),
  ADDRESS2                 varchar(50 ),
  CITY                     varchar(30 ),
  STATE                    varchar(30 ),
  COUNTRY_ID               numeric,
  ZIP                      varchar(10 ),
  ZIP_EXT                  varchar(20 ),
  OBI_POST_URL             varchar(250 ),
  DEL_USER_LOGIN_DAYS      numeric               NOT NULL,
  DEL_CURRENT_ORDERS       numeric               NOT NULL,
  DEL_PLACED_ORDERS        numeric               NOT NULL,
  DUNS_NUMBER              varchar(15 ),
  LAST_MOD_BY              varchar(17 ),
  LAST_MOD_DATE            DATE                 NOT NULL,
  OBI_COMPANY_NAME         varchar(50 ),
  OBI_QUALIFIER_ID         varchar(2 ),
  DAYS_PASSWORD_EXPIRE     numeric,
  CREATION_DATE            DATE,
  CUSTOMER_FAQ_LABEL       varchar(30 ),
  CUSTOMER_FAQ_URL         varchar(256 ),
  DEFAULT_CATALOG_TYPE_ID  numeric(1),
  TIER_ONE_ID              varchar(6 ),
  RESTRICTION_MESSAGE      varchar(256 )
);

CREATE TABLE USER_ACCT_GROUP
(
  USER_ACCT_GRP_ID   numeric(15)                 NOT NULL,
  GROUP_NAME         varchar(30 )          NOT NULL,
  GROUP_DESCRIPTION  varchar(50 ),
  GROUP_STATE        varchar(1 )           DEFAULT 'A'                   NOT NULL,
  ACCOUNT_GROUP      varchar(6 )           NOT NULL,
  ORIGINATOR         varchar(17 ),
  DEFAULT_ACCOUNT    varchar(30 ),
  DEFAULT_CONSIGNEE  varchar(6 ),
  DEFAULT_COST_ID    numeric(15),
  LAST_MOD_DATE      DATE,
  LAST_MOD_BY        varchar(17 )
);

CREATE TABLE GROUP_ACCOUNT
(
  USER_ACCT_GRP_ID  numeric(15)                  NOT NULL,
  SUB_SUPLR_ID      numeric                      NOT NULL,
  ACCOUNT_NUMBER    varchar(30 )           NOT NULL,
  CREATION_DATE     DATE                        DEFAULT SYSDATE
);

CREATE TABLE CONSIGNEE_LIST
(
  ACCOUNT_NUMBER        varchar(30 )       NOT NULL,
  SUB_SUPLR_ID          numeric                  NOT NULL,
  CONSIGNEE             varchar(6 )        NOT NULL,
  PRICING_REGION        varchar(1 )        DEFAULT 'N'                   NOT NULL,
  CONSIGNEE_NAME        varchar(50 ),
  COST_CENTER_TYPE      varchar(1 ),
  CONSIGNEE_STATUS      varchar(1 ),
  ASSIGNED_LOCATION     varchar(3 ),
  ONE_CC_PER_ORDER      varchar(1 ),
  LAST_MOD_BY           varchar(17 ),
  LAST_MOD_DATE         DATE,
  MSA_LOCATION          varchar(3 ),
  ADDRESS               varchar(50 ),
  CITY                  varchar(30 ),
  STATE                 varchar(30 ),
  ZIP                   varchar(10 ),
  ZIP_EXT               varchar(20 ),
  VOICE_AREA            varchar(10 ),
  VOICE_PHONE           varchar(15 ),
  VOICE_EXT             varchar(8 ),
  VOICE_COUNTRY_CODE    varchar(7 ),
  FAX_AREA              varchar(10 ),
  FAX_PHONE             varchar(15 ),
  FAX_COUNTRY_CODE      varchar(7 ),
  SREP_NAME             varchar(15 ),
  SIC_CODE              numeric,
  RELEASE_NUM_REQD      varchar(1 )        DEFAULT 'N',
  ACCT_USE_CREDIT_CARD  varchar(1 ),
  SEARCH_ORDER          varchar(1 ),
  ACCOUNT_PO            varchar(22 ),
  CONSIGNEE_DESC        varchar(125 ),
  CREATION_DATE         DATE                    DEFAULT sysdate,
  RESIDENTIAL_DELV_IND  varchar(1 ),
  DEPARTMENT_CODE       varchar(2 )
);

CREATE TABLE GROUP_CONSIGNEE
(
  CONSIGNEE         varchar(6 )            NOT NULL,
  ACCOUNT_NUMBER    varchar(30 )           NOT NULL,
  SUB_SUPLR_ID      numeric                      NOT NULL,
  USER_ACCT_GRP_ID  numeric(15)                  NOT NULL,
  CREATION_DATE     DATE                        DEFAULT SYSDATE
);

CREATE TABLE COST_CENTER_LIST
(
  COST_ID             numeric(15)                NOT NULL,
  COST_CENTER         varchar(24 )         NOT NULL,
  ACCOUNT_NUMBER      varchar(30 ),
  SUB_SUPLR_ID        numeric,
  CONSIGNEE           varchar(6 ),
  COST_CENTER_DESC    varchar(50 ),
  COST_CENTER_STATUS  varchar(1 ),
  LAST_MOD_BY         varchar(17 ),
  LAST_MOD_DATE       DATE,
  CREATION_DATE       DATE                      DEFAULT sysdate
);

CREATE TABLE GROUP_COST_CENTER
(
  USER_ACCT_GRP_ID  numeric(15)                  NOT NULL,
  COST_ID           numeric(15)                  NOT NULL
);

--SQL Statement which produced this data:
--
--  select * from authentication where account_group = '268315'
--
Insert into AUTHENTICATION
   (USER_KEY, SUPLR_ID, ACCOUNT_GROUP, PASSWORD, LOGIN_TYPE, LOGIN_STATE, PERMISSION_GRP_ID, ALLOW_MULTIPLE_LOGINS, ALLOW_OBI, LOGIN_ID, ALLOW_ORDERPOINT, CREATION_DATE, COPYMAX_USER_LEVEL, ONLINE_REPORTING_STATUS, SHOW_THUMBS, LOCK_CTR)
 Values
   ('268315reguser977', 1, '268315', 'oWU3rVngQuh3Dg==', 'R', 
    'A', 1634007, 
    'N', 'N', 
    '268315reguser977', 'Y', CURDATE(), 'N', 'I', 'Y', 0);
Insert into AUTHENTICATION
   (USER_KEY, SUPLR_ID, ACCOUNT_GROUP, PASSWORD, LOGIN_TYPE, LOGIN_STATE, PERMISSION_GRP_ID, USER_ACCT_GRP_ID, LAST_LOGIN_DATE, ALLOW_MULTIPLE_LOGINS, ALLOW_OBI, START_PAGE_ID, DEFAULT_CATALOG, PASSWORD_CHANGE_DATE, LAST_MOD_DATE, LOGIN_ID, ALLOW_ORDERPOINT, CREATION_DATE, CAN_USE_EMAIL_ACK, PIN_ASSIGNMENT_STATE, COPYMAX_USER_LEVEL, ONLINE_REPORTING_STATUS, SHOW_THUMBS, ONLINE_SPEND_REPORTING_STATUS, LOCK_CTR)
 Values
   ('268315ssharpad950', 1, '268315', 'r0gDtg7QMo5yQQ==', 'C', 
    'A', 1634002, 1336070, 
    CURDATE(), 'N', 'N', 
    1, 'F', CURDATE(), 
    'wg1admin', 'Y', CURDATE(), 'Y', '0', 'N', 'I', 'Y', 'I', 0);
Insert into AUTHENTICATION
   (USER_KEY, SUPLR_ID, ACCOUNT_GROUP, PASSWORD, LOGIN_TYPE, LOGIN_STATE, PERMISSION_GRP_ID, USER_ACCT_GRP_ID, LAST_LOGIN_DATE, ALLOW_MULTIPLE_LOGINS, ALLOW_OBI, START_PAGE_ID, DEFAULT_CATALOG, PASSWORD_CHANGE_DATE, LAST_MOD_DATE, LOGIN_ID, ALLOW_ORDERPOINT, CREATION_DATE, CAN_USE_EMAIL_ACK, PIN_ASSIGNMENT_STATE, COPYMAX_USER_LEVEL, ONLINE_REPORTING_STATUS, SHOW_THUMBS, ONLINE_SPEND_REPORTING_STATUS, LOCK_CTR)
 Values
   ('268315wwg1user939', 1, '268315', 'r0gDtg7QMo5yQQ==', 'C', 
    'A', 1634006, 1336070, 
    CURDATE(), 'N', 'N', 
    1, 'F', CURDATE(), CURDATE(),
    'wg1user1', 'Y', CURDATE(), 'Y', '5', 'N', 'I', 'Y', 'I', 0);
Insert into AUTHENTICATION
   (USER_KEY, SUPLR_ID, ACCOUNT_GROUP, PASSWORD, LOGIN_TYPE, LOGIN_STATE, PERMISSION_GRP_ID, USER_ACCT_GRP_ID, ALLOW_MULTIPLE_LOGINS, ALLOW_OBI, START_PAGE_ID, DEFAULT_CATALOG, PASSWORD_CHANGE_DATE, LAST_MOD_DATE, LOGIN_ID, ALLOW_ORDERPOINT, CREATION_DATE, CAN_USE_EMAIL_ACK, PIN_ASSIGNMENT_STATE, COPYMAX_USER_LEVEL, ONLINE_REPORTING_STATUS, SHOW_THUMBS, ONLINE_SPEND_REPORTING_STATUS, LOCK_CTR)
 Values
   ('268315wwg1user269', 1, '268315', 'r0gDtg7QMo5yQQ==', 'C', 
    'A', 1634006, 1336071, 
    'N', 'N', 
    1, 'F', CURDATE(), CURDATE(),
    'wg1user2', 'Y', CURDATE(),'Y', '5', 'N', 'I', 'Y', 'I', 0);
Insert into AUTHENTICATION
   (USER_KEY, SUPLR_ID, ACCOUNT_GROUP, PASSWORD, LOGIN_TYPE, LOGIN_STATE, PERMISSION_GRP_ID, USER_ACCT_GRP_ID, ALLOW_MULTIPLE_LOGINS, ALLOW_OBI, START_PAGE_ID, DEFAULT_CATALOG, PASSWORD_CHANGE_DATE, LAST_MOD_DATE, LOGIN_ID, ALLOW_ORDERPOINT, CREATION_DATE, CAN_USE_EMAIL_ACK, PIN_ASSIGNMENT_STATE, COPYMAX_USER_LEVEL, ONLINE_REPORTING_STATUS, SHOW_THUMBS, ONLINE_SPEND_REPORTING_STATUS, LOCK_CTR)
 Values
   ('268315wwg1user377', 1, '268315', 'r0gDtg7QMo5yQQ==', 'C', 
    'A', 1634006, 1336072, 
    'N', 'N', 
    1, 'F', CURDATE(), CURDATE(),
    'wg1user3', 'Y', CURDATE(), 'Y', '5', 'N', 'I', 'Y', 'I', 0);



--SQL Statement which produced this data:
--
--  select * from info where user_key in (select user_key from authentication where account_group = '268315')
--
Insert into INFO
   (USER_KEY, MR_MS, FIRST_NAME, LAST_NAME, ADDRESS1, CITY, STATE, ZIP, VOICE_AREA, VOICE_PHONE, DATE_REGISTERED, EMAIL, COUNTRY_ID, USER_KEY_NAME, TITLE_NUMBER, ALLOW_PROMO, CREATION_DATE)
 Values
   ('268315ssharpad950', 'None', 'sharpadmin', 
    'sharpadmin', '123 shuman blvd', 'Naperville', 
    'IL', '60563', '630', 
    '1111111', CURDATE(), 'sampathramanujam@officemax.com', 1, 'ssharpad', 25, 'N', CURDATE());
Insert into INFO
   (USER_KEY, MR_MS, FIRST_NAME, LAST_NAME, ADDRESS1, CITY, STATE, ZIP, VOICE_AREA, VOICE_PHONE, DATE_REGISTERED, ACTIVATED_BY, DATE_ACTIVATED, COUNTRY_ID, USER_KEY_NAME, TITLE_NUMBER, ALLOW_PROMO, CREATION_DATE)
 Values
   ('268315wwg1user939', 'None', 'wg1user1', 
    'wg1user1', '123 shuman blvd', 'Naperville', 
    'IL', '60563', '630', 
    '1111111', CURDATE(), '268315ssharpad950', CURDATE(), 1, 'wwg1user', 0, 'N', CURDATE());
Insert into INFO
   (USER_KEY, MR_MS, FIRST_NAME, LAST_NAME, ADDRESS1, CITY, STATE, ZIP, VOICE_AREA, VOICE_PHONE, DATE_REGISTERED, ACTIVATED_BY, DATE_ACTIVATED, COUNTRY_ID, USER_KEY_NAME, TITLE_NUMBER, ALLOW_PROMO, CREATION_DATE)
 Values
   ('268315wwg1user269', 'None', 'wg1user2', 
    'wg1user2', '123 shuman blvd', 'Naperville', 
    'IL', '60563', '630', 
    '1111111', CURDATE(), '268315ssharpad950', CURDATE(),1, 'wwg1user', 0, 'N', CURDATE());
Insert into INFO
   (USER_KEY, MR_MS, FIRST_NAME, LAST_NAME, ADDRESS1, CITY, STATE, ZIP, VOICE_AREA, VOICE_PHONE, DATE_REGISTERED, ACTIVATED_BY, DATE_ACTIVATED, COUNTRY_ID, USER_KEY_NAME, TITLE_NUMBER, ALLOW_PROMO, CREATION_DATE)
 Values
   ('268315wwg1user377', 'None', 'wg1user3', 
    'wg1user3', '123 shuman blvd', 'Naperville', 
    'IL', '60563', '630', 
    '1111111', CURDATE(), '268315ssharpad950', CURDATE(), 1, 'wwg1user', 0, 'N', CURDATE());



--SQL Statement which produced this data:
--
--  select * from user_acct_group where account_group = '268315'
--
Insert into USER_ACCT_GROUP
   (USER_ACCT_GRP_ID, GROUP_NAME, GROUP_DESCRIPTION, GROUP_STATE, ACCOUNT_GROUP, ORIGINATOR, DEFAULT_ACCOUNT, DEFAULT_COST_ID, LAST_MOD_DATE, LAST_MOD_BY)
 Values
   (1336070, 'ALL_ACCESS', 'Group that can access all accounts', 'A', '268315', 
    '029393LIngram864', '0020001', 2498943, CURDATE(), '268315ssharpad950');
Insert into USER_ACCT_GROUP
   (USER_ACCT_GRP_ID, GROUP_NAME, GROUP_DESCRIPTION, GROUP_STATE, ACCOUNT_GROUP, ORIGINATOR, DEFAULT_ACCOUNT, DEFAULT_CONSIGNEE, LAST_MOD_DATE, LAST_MOD_BY)
 Values
   (1336071, 'WG1PG2', 'WG1PG2', 'A', '268315', 
    '268315ssharpad950', '0020006', 'A6C003', CURDATE(), '268315ssharpad950');
Insert into USER_ACCT_GROUP
   (USER_ACCT_GRP_ID, GROUP_NAME, GROUP_DESCRIPTION, GROUP_STATE, ACCOUNT_GROUP, ORIGINATOR, DEFAULT_ACCOUNT, DEFAULT_CONSIGNEE, DEFAULT_COST_ID, LAST_MOD_DATE, LAST_MOD_BY)
 Values
   (1336072, 'WG1PG3', 'WG1 PG3 Group having only A2', 'A', '268315', 
    '268315ssharpad950', '0020002', 'A2C001', 2498946, CURDATE(), 
    '268315ssharpad950');


--SQL Statement which produced this data:
--
--  select * from account_group where company_id = 181503 and account_group_name like 'WG%'
--
Insert into ACCOUNT_GROUP
   (ACCOUNT_GROUP, ACCOUNT_GROUP_STATE, COMPANY_ID, SUPLR_ID, ACCOUNT_GROUP_NAME, ORIGINATOR, ACCOUNT_GROUP_TYPE, USE_CUSTOM_FIELDNAMES, ADDRESS1, CITY, STATE, COUNTRY_ID, ZIP, DEL_USER_LOGIN_DAYS, DEL_CURRENT_ORDERS, DEL_PLACED_ORDERS, LAST_MOD_BY, LAST_MOD_DATE, OBI_QUALIFIER_ID, DAYS_PASSWORD_EXPIRE, CREATION_DATE, TIER_ONE_ID, RESTRICTION_MESSAGE)
 Values
   ('268315', 'A', 181503, 1, 'WG1', 
    '029393LIngram864', 'T', 'N', '123 shuman blvd', 
    'Naperville', 'IL', 1, '60563', 
    120, 90, 90, 
    '029393LIngram864', CURDATE(), '01', 0, CURDATE(), 'XX', 'There are no items that match your request, or the item that you searched for is not available at your company''s request. For more information please contact your purchasing department or our customer service.');


--SQL Statement which produced this data:
--
--  select * from account_number_list where account_number like '002000%'
--
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020001', 1, 181503, 'A', 'X', 
    'A1', 'N', 'A', 'N', '000', 
    'T', 'N', '029393LIngram864', CURDATE(), 'R', 
    'N', 'C', CURDATE(), 'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020002', 1, 181503, 'A', 'N', 
    'A2', 'N', 'A', 'Y', '000', 
    'T', 'N', '029393LIngram864', CURDATE(), 'R', 
    'N', 'C', CURDATE(), 'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020003', 1, 181503, 'A', 'X', 
    'A3', 'N', 'A', 'N', '000', 
    'T', 'N', '029393LIngram864', CURDATE(), 'R', 
    'N', 'C', CURDATE(), 'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020004', 1, 181503, 'A', 'X', 
    'A4', 'N', 'A', 'Y', '000', 
    'T', 'N', '029393LIngram864', CURDATE(), 'R', 
    'N', 'C', CURDATE(), 'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020005', 1, 181503, 'A', 'E', 
    'A5', 'N', 'A', 'Y', '000', 
    'T', 'N', '029393LIngram864', CURDATE(), 'R', 
    'N', 'C', CURDATE(), 'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020006', 1, 181503, 'A', 'E', 
    'A6', 'N', 'A', 'Y', '000', 
    'T', 'N', '029393LIngram864', CURDATE(), 'R', 
    'N', 'C', CURDATE(), 'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020007', 1, 181503, 'A', 'E', 
    'A7', 'N', 'A', 'N', '000', 
    'T', 'N', '029393LIngram864', CURDATE(),'R', 
    'N', 'C', CURDATE(),'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020008', 1, 181503, 'A', 'N', 
    'A8', 'N', 'A', 'N', '000', 
    'T', 'N', '029393LIngram864', CURDATE(),'R', 
    'N', 'C', CURDATE(),'N', 
    'None', 'N', 'XX', 'N');
Insert into ACCOUNT_NUMBER_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, COMPANY_ID, ACCOUNT_NUMBER_STATE, COST_CENTER_TYPE, ACCOUNT_NAME, ONE_CC_PER_ORDER, ACCOUNT_STATUS, USE_CONSIGNEE, LOCATION_CODE, ACCOUNT_TYPE, GSA_ACCOUNT, LAST_MOD_BY, LAST_MOD_DATE, ACCOUNT_LOAD_STATUS, SYNC_ACCOUNT_INFO, CONTRACT_PROCESS, CREATION_DATE, USE_ALLOCATION_LIST, DEFAULT_COST_CENTER, BYPASS_DEFAULT_CATALOG, TIER_ONE_ID, NO_SPECIAL_ORDERS)
 Values
   ('0020009', 1, 181503, 'A', 'N', 
    'A9', 'N', 'A', 'Y', '000', 
    'T', 'N', '029393LIngram864', CURDATE(),'R', 
    'N', 'C', CURDATE(),'N', 
    'None', 'N', 'XX', 'N');


--SQL Statement which produced this data:
--
--  select * from group_account where user_acct_grp_id in (select user_acct_grp_id from user_acct_group where account_group = '268315')
--
Insert into GROUP_ACCOUNT
   (USER_ACCT_GRP_ID, SUB_SUPLR_ID, ACCOUNT_NUMBER, CREATION_DATE)
 Values
   (1336070, 1, '0020006', CURDATE());
Insert into GROUP_ACCOUNT
   (USER_ACCT_GRP_ID, SUB_SUPLR_ID, ACCOUNT_NUMBER, CREATION_DATE)
 Values
   (1336070, 1, '0020002', CURDATE());
Insert into GROUP_ACCOUNT
   (USER_ACCT_GRP_ID, SUB_SUPLR_ID, ACCOUNT_NUMBER, CREATION_DATE)
 Values
   (1336070, 1, '0020001', CURDATE());
Insert into GROUP_ACCOUNT
   (USER_ACCT_GRP_ID, SUB_SUPLR_ID, ACCOUNT_NUMBER, CREATION_DATE)
 Values
   (1336071, 1, '0020002', CURDATE());
Insert into GROUP_ACCOUNT
   (USER_ACCT_GRP_ID, SUB_SUPLR_ID, ACCOUNT_NUMBER, CREATION_DATE)
 Values
   (1336071, 1, '0020006', CURDATE());
Insert into GROUP_ACCOUNT
   (USER_ACCT_GRP_ID, SUB_SUPLR_ID, ACCOUNT_NUMBER, CREATION_DATE)
 Values
   (1336072, 1, '0020002', CURDATE());


--SQL Statement which produced this data:
--
--  select * from consignee_list where  account_number in (select account_number from account_number_list where company_id = 181503 and account_number like '002000%')
--
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020001', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(),CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020002', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(),CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, COST_CENTER_TYPE, CONSIGNEE_STATUS, ONE_CC_PER_ORDER, LAST_MOD_BY, LAST_MOD_DATE, MSA_LOCATION, ADDRESS, CITY, STATE, ZIP, SIC_CODE, CONSIGNEE_DESC, CREATION_DATE)
 Values
   ('0020002', 1, 'A2C001', 'N', 
    'X', 'A', 'N', '268315ssharpad950', 
    CURDATE(),'000', '523 shuman blv', 'napterville', 'IL', 
    '60563', 0, 'A2 Consignee 1', CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, COST_CENTER_TYPE, CONSIGNEE_STATUS, ONE_CC_PER_ORDER, LAST_MOD_BY, LAST_MOD_DATE, MSA_LOCATION, ADDRESS, CITY, STATE, ZIP, SIC_CODE, CONSIGNEE_DESC, CREATION_DATE)
 Values
   ('0020002', 1, 'A2C002', 'N', 
    'N', 'A', 'N', '268315ssharpad950', 
    CURDATE(),'000', '523 shuman blv', 'napterville', 'IL', 
    '60563', 0, 'A2 Consignee 2', CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020003', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(), CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020004', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(), CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020005', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(), CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020006', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(), CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, COST_CENTER_TYPE, CONSIGNEE_STATUS, ONE_CC_PER_ORDER, LAST_MOD_BY, LAST_MOD_DATE, MSA_LOCATION, ADDRESS, CITY, STATE, ZIP, SIC_CODE, CONSIGNEE_DESC, CREATION_DATE)
 Values
   ('0020006', 1, 'A6C001', 'N', 
    'X', 'A', 'N', '268315ssharpad950', 
    CURDATE(), '000', '523 shuman blv', 'napterville', 'IL', 
    '60563', 0, 'A6 Consignee One', CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, COST_CENTER_TYPE, CONSIGNEE_STATUS, ONE_CC_PER_ORDER, LAST_MOD_BY, LAST_MOD_DATE, MSA_LOCATION, ADDRESS, CITY, STATE, ZIP, SIC_CODE, CONSIGNEE_DESC, CREATION_DATE)
 Values
   ('0020006', 1, 'A6C002', 'N', 
    'E', 'A', 'N', '268315ssharpad950', 
    CURDATE(),'000', '523 shuman blv', 'napterville', 'IL', 
    '60563', 0, 'A6 Consignee 2', CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, COST_CENTER_TYPE, CONSIGNEE_STATUS, ONE_CC_PER_ORDER, LAST_MOD_BY, LAST_MOD_DATE, MSA_LOCATION, ADDRESS, CITY, STATE, ZIP, SIC_CODE, CONSIGNEE_DESC, CREATION_DATE)
 Values
   ('0020006', 1, 'A6C003', 'N', 
    'N', 'A', 'N', '268315ssharpad950', 
    CURDATE(),'000', '523 shuman blv', 'napterville', 'IL', 
    '60563', 0, 'A6 Consignee 3', CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020007', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(),CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020008', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(),CURDATE());
Insert into CONSIGNEE_LIST
   (ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, PRICING_REGION, CONSIGNEE_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   ('0020009', 1, ' ', 'N', 
    'A', '029393LIngram864', CURDATE(),CURDATE());


--SQL Statement which produced this data:
--
--  select * from group_consignee where user_acct_grp_id in  (select user_acct_grp_id from user_acct_group where account_group = '268315')
--
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   (' ', '0020001', 1, 1336070, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A2C001', '0020002', 1, 1336070, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A6C003', '0020006', 1, 1336070, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A2C002', '0020002', 1, 1336070, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A6C001', '0020006', 1, 1336070, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A6C002', '0020006', 1, 1336070, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A6C003', '0020006', 1, 1336071, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A2C002', '0020002', 1, 1336071, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A6C002', '0020006', 1, 1336071, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A2C001', '0020002', 1, 1336072, CURDATE());
Insert into GROUP_CONSIGNEE
   (CONSIGNEE, ACCOUNT_NUMBER, SUB_SUPLR_ID, USER_ACCT_GRP_ID, CREATION_DATE)
 Values
   ('A2C002', '0020002', 1, 1336072, CURDATE());


--SQL Statement which produced this data:
--
--  select * from cost_center_list where account_number in (select account_number from account_number_list where company_id = 181503 and account_number like '002000%')
--
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498942, 'A1CC01', '0020001', 1, ' ', 
    'A1CC01 Free from Acct cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498943, 'A1CC02', '0020001', 1, ' ', 
    'A1CC02 Free form Acct cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498944, 'A1CC03', '0020001', 1, ' ', 
    'A1CC03 Free form Acct cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498945, 'A2C1CC1', '0020002', 1, 'A2C001', 
    'A2C1CC1 site Cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498946, 'A2C1CC2', '0020002', 1, 'A2C001', 
    'A2C1CC2 site cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498940, 'A6CC1', '0020006', 1, ' ', 
    'A6CC1 acct level cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498941, 'A6CC2', '0020006', 1, ' ', 
    'A6CC2 acct level cost center', 'A', '268315ssharpad950', CURDATE(),CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498936, 'A6C1CC1', '0020006', 1, 'A6C001', 
    'A6C1CC1', 'A', '268315ssharpad950', CURDATE(), CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498937, 'A6C1CC2', '0020006', 1, 'A6C001', 
    'A6C1CC2', 'A', '268315ssharpad950', CURDATE(), CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498938, 'A6C2CC1', '0020006', 1, 'A6C002', 
    'A6C2CC1', 'A', '268315ssharpad950', CURDATE(), CURDATE());
Insert into COST_CENTER_LIST
   (COST_ID, COST_CENTER, ACCOUNT_NUMBER, SUB_SUPLR_ID, CONSIGNEE, COST_CENTER_DESC, COST_CENTER_STATUS, LAST_MOD_BY, LAST_MOD_DATE, CREATION_DATE)
 Values
   (2498939, 'A6C2CC2', '0020006', 1, 'A6C002', 
    'A6C2CC2', 'A', '268315ssharpad950', CURDATE(), CURDATE());



--SQL Statement which produced this data:
--
--  select * from group_cost_center where user_acct_grp_id in  (select user_acct_grp_id from user_acct_group where account_group = '268315')
--
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498941);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498943);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498938);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498942);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498946);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498940);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498939);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498937);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498936);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336070, 2498945);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336071, 2498939);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336071, 2498938);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336072, 2498945);
Insert into GROUP_COST_CENTER
   (USER_ACCT_GRP_ID, COST_ID)
 Values
   (1336072, 2498946);





