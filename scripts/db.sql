create table public.bank(
	bankId serial not null,
	new_user char(1) not null default 'Y',
	account_email varchar null,
	account_password varchar null
);


create table public.customer(
	customerId serial not null,
	email varchar null,
	Checking dec(18,2) null,
	Saving dec(18,2) null
);
