#!/bin/bash

script_file="data.sql"
echo "" > $script_file

for i in {1..50000}; do
    operation=$((1 + RANDOM % 3))

    if [ $operation -eq 1 ]; then
        first_name="FirstName$i"
        last_name="LastName$i"
        email="email$i@example.com"
        phone_number="123-456-7890"
        address="Address$i"
        echo "INSERT INTO customers (first_name, last_name, email, phone_number, address) VALUES ('$first_name', '$last_name', '$email', '$phone_number', '$address');" >> $script_file
    elif [ $operation -eq 2 ]; then
        customer_id=$((1 + RANDOM % i))
        phone_number="987-654-3210"
        echo "UPDATE customers SET phone_number = '$phone_number' WHERE customer_id = $customer_id;" >> $script_file
    else
        customer_id=$((1 + RANDOM % i))
        echo "DELETE FROM customers WHERE customer_id = $customer_id;" >> $script_file
    fi
done

echo "Script '$script_file' created."