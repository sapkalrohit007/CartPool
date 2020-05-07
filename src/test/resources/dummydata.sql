/* Add store */
INSERT INTO cartpool.STORE values(1,"San Jose","CA","El Camino","95345","NIB");

/* Add products */
INSERT INTO cartpool.product values (1,"Cadbury","Cookies","abc","Oreo","10","PIECE");
INSERT INTO cartpool.product values (2,"Pepsico","Chips","abc","Lays","5","PIECE");

/* Add product store mapping */
INSERT INTO cartpool.product_store values(1,1);
INSERT INTO cartpool.product_store values(2,1);

/* Add order  */
/* Needs to update userid(which is the first field) as per current users  */
INSERT INTO cartpool.orders values(1,cast('3-May-2020' AS datetime), "PENDING", 1, NULL, 1)

/* Add order product mappings */
INSERT INTO cartpool.orderdetails values(1,5,1,1);
INSERT INTO cartpool.orderdetails values(1,6,1,2);