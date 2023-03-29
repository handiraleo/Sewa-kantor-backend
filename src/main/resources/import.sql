INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 0, 'ADMIN', 'Super Admin','');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 1, 'ADMIN', 'Supervisor','');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 2, 'ADMIN', 'Consultant','');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 3, 'CUSTOMER', 'Jhon', 'Doe');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 3, 'CUSTOMER', 'Customer', '1');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 3, 'CUSTOMER', 'Customer', '2');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 3, 'CUSTOMER', 'Customer', '3');
INSERT INTO users (password, role, user_type, first_name, last_name) VALUES ('$2a$12$T8Kw20YmyCMYicuVsLsTpeyiIqJk/m3McXk64KizlRZ//GXDaRwf.', 3, 'CUSTOMER', 'Customer', '4');

INSERT INTO admin (user_id, username) VALUES (1, 'superadmin');
INSERT INTO admin (user_id, username) VALUES (2, 'supervisor');
INSERT INTO admin (user_id, username) VALUES (3, 'consultant');
INSERT INTO customers (user_id, email) VALUES (4, 'jhondoe@mail.com');
INSERT INTO customers (user_id, email) VALUES (5, 'customer1@mail.com');
INSERT INTO customers (user_id, email) VALUES (6, 'customer2@mail.com');
INSERT INTO customers (user_id, email) VALUES (7, 'customer3@mail.com');
INSERT INTO customers (user_id, email) VALUES (8, 'customer4@mail.com');

INSERT INTO cities (name) VALUES ('Jakarta');

INSERT INTO complexes (city_id, name, description) VALUES (1, 'Anggrek Permai', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit a sem sit amet tincidunt. Donec sit amet leo sit amet urna viverra sagittis. Morbi neque purus, volutpat sit amet augue eget, finibus faucibus lacus. Sed sed metus sit amet turpis gravida vulputate.');

INSERT INTO buildings (complex_id, name, address, description) VALUES (1, 'BCA Tower', '50/F, Menara BCA Grand Indonesia, Jakarta, 10310', 'Enjoy stunning views from the 50th floor of BCA Tower, located across the street from the famous Bundaran Hotel Indonesia (Bundaran HI). Widely regarded as the centre of Jakarta, the area offers excellent public transport and a supportive business environment.');
INSERT INTO buildings (complex_id, name, address, description) VALUES (1, 'Menara Astra', 'Menara Astra, 37th Floor. Jl. Jend. Sudirman Kav. 5-6, Jakarta, 10220', 'Enjoy the stunning view from from the 50th floor of BCA Tower, located across the street from the famous Bundaran Hotel Indonesia (Bundaran HI). Widely regarded as the center of Jakarta.');

INSERT INTO building_images (name, path, building_id) VALUES ('BCA Tower 1', '/buildings/bca-tower-1.jpg', 1);
INSERT INTO building_images (name, path, building_id) VALUES ('Menara Astra 1', '/buildings/menara-astra-1.jpg', 2);

INSERT INTO units (building_id, name, capacity, length, width, height, type, description) VALUES (1, 'meeting room 1', 10, 4, 3, 3.5, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit a sem sit amet tincidunt. Donec sit amet leo sit amet urna viverra sagittis.');
INSERT INTO units (building_id, name, capacity, length, width, height, type, description) VALUES (1, 'meeting room 2', 12, 4, 4, 3.5, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit a sem sit amet tincidunt. Donec sit amet leo sit amet urna viverra sagittis.');
INSERT INTO units (building_id, name, capacity, length, width, height, type, description) VALUES (1, 'office room 1', 30, 7, 6, 3.5, 0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit a sem sit amet tincidunt. Donec sit amet leo sit amet urna viverra sagittis.');

INSERT INTO units (building_id, name, capacity, length, width, height, type, description) VALUES (2, 'meeting room 1', 10, 4, 3, 3.5, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit a sem sit amet tincidunt. Donec sit amet leo sit amet urna viverra sagittis.');
INSERT INTO units (building_id, name, capacity, length, width, height, type, description) VALUES (2, 'meeting room 2', 12, 4, 4, 3.5, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse blandit a sem sit amet tincidunt. Donec sit amet leo sit amet urna viverra sagittis.');

INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 1', '/units/unit1-1.jpg', 1);
INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 2', '/units/unit1-2.jpg', 1);

INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 1', '/units/unit2-1.jpg', 2);
INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 2', '/units/unit2-2.jpg', 2);

INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 1', '/units/unit3-1.jpg', 3);
INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 2', '/units/unit3-2.jpg', 3);

INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 1', '/units/unit4-1.jpg', 4);
INSERT INTO unit_images (name, path, unit_id) VALUES ('photo 2', '/units/unit4-2.jpg', 4);

INSERT INTO prices(price, type, unit_id) VALUES (50000, 0, 1);
INSERT INTO prices(price, type, unit_id) VALUES (250000, 1, 1);
INSERT INTO prices(price, type, unit_id) VALUES (1500000, 3, 1);
INSERT INTO prices(price, type, unit_id) VALUES (5000000, 3, 1);
INSERT INTO prices(price, type, unit_id) VALUES (2500000, 4, 1);

INSERT INTO prices(price, type, unit_id) VALUES (1000000, 2, 2);
INSERT INTO prices(price, type, unit_id) VALUES (3000000, 3, 2);
INSERT INTO prices(price, type, unit_id) VALUES (30000000, 4, 2);

INSERT INTO prices(price, type, unit_id) VALUES (4000000, 3, 3);
INSERT INTO prices(price, type, unit_id) VALUES (40000000, 4, 3);

INSERT INTO prices(price, type, unit_id) VALUES (4000000, 3, 5);
INSERT INTO prices(price, type, unit_id) VALUES (40000000, 4, 5);

INSERT INTO prices(price, type, unit_id) VALUES (50000, 0, 4);
INSERT INTO prices(price, type, unit_id) VALUES (250000, 1, 4);
INSERT INTO prices(price, type, unit_id) VALUES (1500000, 3, 4);
INSERT INTO prices(price, type, unit_id) VALUES (5000000, 3, 4);
INSERT INTO prices(price, type, unit_id) VALUES (2500000, 4, 4);

INSERT INTO reviews(unit_id, customer_id, star) VALUES (1, 4, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (1, 5, 5);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (1, 6, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (1, 7, 3);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (1, 8, 5);

INSERT INTO reviews(unit_id, customer_id, star) VALUES (2, 4, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (2, 5, 5);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (2, 6, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (2, 7, 3);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (2, 8, 2);

INSERT INTO reviews(unit_id, customer_id, star) VALUES (3, 4, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (3, 5, 5);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (3, 6, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (3, 7, 3);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (3, 8, 3);

INSERT INTO reviews(unit_id, customer_id, star) VALUES (4, 4, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (4, 5, 5);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (4, 6, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (4, 7, 3);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (4, 8, 4);

INSERT INTO reviews(unit_id, customer_id, star) VALUES (5, 4, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (5, 5, 5);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (5, 6, 4);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (5, 7, 5);
INSERT INTO reviews(unit_id, customer_id, star) VALUES (5, 8, 5);

INSERT INTO facilities (name, icon) VALUES ('Free Snack', '/facilities/free-snack.png');
INSERT INTO facilities (name, icon) VALUES ('Business Lounge', '/facilities/business-lounge.png');
INSERT INTO facilities (name, icon) VALUES ('Parking Area', '/facilities/parking-area.png');
INSERT INTO facilities (name, icon) VALUES ('Downtown', '/facilities/downtown.png');
INSERT INTO facilities (name, icon) VALUES ('Free Area', '/facilities/free-area.png');

INSERT INTO building_facilities (building_id, facility_id) VALUES (1, 1);
INSERT INTO building_facilities (building_id, facility_id) VALUES (1, 2);
INSERT INTO building_facilities (building_id, facility_id) VALUES (1, 3);
INSERT INTO building_facilities (building_id, facility_id) VALUES (1, 4);
INSERT INTO building_facilities (building_id, facility_id) VALUES (1, 5);

INSERT INTO building_facilities (building_id, facility_id) VALUES (2, 1);
INSERT INTO building_facilities (building_id, facility_id) VALUES (2, 2);
INSERT INTO building_facilities (building_id, facility_id) VALUES (2, 3);
INSERT INTO building_facilities (building_id, facility_id) VALUES (2, 4);

INSERT INTO nearby_places (building_id, name, distance) VALUES (1, 'Gelora Bung Karno Stadium', 0.1);
INSERT INTO nearby_places (building_id, name, distance) VALUES (2, 'Bundaran HI', 0.5);