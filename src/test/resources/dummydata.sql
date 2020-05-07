/* Add store */
INSERT INTO cartpool.STORE values(1,"San Jose","CA","El Camino","95345","NIB");

/* Add products */
INSERT INTO cartpool.product values (1,"Cadbury","Cookies","abc","Oreo","10","PIECE");
INSERT INTO cartpool.product values (2,"Pepsico","Chips","abc","Lays","5","PIECE");

/* Add product store mapping */
INSERT INTO cartpool.product_store values(1,1);
INSERT INTO cartpool.product_store values(1,2);

/* Add order  */
/* Needs to update userid(which is the first field) as per current users  */
INSERT INTO cartpool.orders values(1,NOW(), "PENDING", 1, NULL, 1);

/* Add order product mappings */
INSERT INTO cartpool.order_details values(1,5,1,1);
INSERT INTO cartpool.order_details values(1,6,1,2);