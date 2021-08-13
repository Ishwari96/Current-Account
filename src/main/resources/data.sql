insert into customer (id,creationTime,name,surname) VALUES (1,CURRENT_TIMESTAMP,'Ishwari','Doshi');
insert into customer (id,creationTime,name,surname) VALUES (2,CURRENT_TIMESTAMP + interval '2' hour,'Stefan','Martine');
insert into customer (id,creationTime,name,surname) VALUES (3,CURRENT_TIMESTAMP,'Sebnem','Ozec');
insert into customer (id,creationTime,name,surname) VALUES (4,CURRENT_TIMESTAMP + interval '1' hour,'Erik','Dijk');
insert into customer (id,creationTime,name,surname) VALUES (5,CURRENT_TIMESTAMP,'TOMMY','TOM');

ALTER TABLE ACCOUNT
    ADD FOREIGN KEY (CUSTOMERID)
    REFERENCES CUSTOMER (ID);

ALTER TABLE TRANSACTION
    ADD FOREIGN KEY (ACCOUNTID)
    REFERENCES ACCOUNT (ID);