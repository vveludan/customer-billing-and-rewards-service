create table if not exists customer (
	id varchar(36) unique not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	address varchar(255) not null,
	primary key (id)
);

create table if not exists transaction (
	id varchar(36) unique not null,
	bill_date date not null,
	bill_amt double not null,
	rwrd_pts int,
	customer_id bigint not null,
	primary key (id),
	CONSTRAINT FK_TRANSACTION_CUSTOMER FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID)
);

