CREATE TABLE public.bank (
	bankid serial NOT NULL,
	new_user bpchar(1) NOT NULL DEFAULT 'Y'::bpchar,
	account_email varchar NOT NULL,
	CONSTRAINT bank_pk PRIMARY KEY (bankid, account_email)
);


CREATE TABLE public.customer (
	customerid serial NOT NULL,
	email varchar NULL,
	checking numeric(18,2) NULL,
	saving numeric(18,2) NULL,
	"password" varchar NULL,
	CONSTRAINT customer_fk FOREIGN KEY (customerid, email) REFERENCES bank(bankid, account_email)
);

