import pandas as pd
import psycopg2 as pg
import datetime
import random


df = pd.read_csv("src/main/ddl/Customer_Names.csv")[['First Name', 'Last Name']]



names_list = [a[0] + " " + a[1] for a in df.values.tolist()]
# print(names_list)

values = []

for name in names_list:
    user_name = name.split()
    user_name = user_name[0].lower()[0] + user_name[1].lower() + str(random.randint(0, 900))
    email_addr = "testing@email.com"
    password = "123456placeholder"
    profile_img = "placeholder"
    skill_lvl = random.randint(50, 7500)
    acc_date = datetime.datetime.now()
    values.append(f"('{user_name}', '{name}', '{email_addr}', '{password}', '{profile_img}', {skill_lvl}, '{acc_date}')")
    # query = f'''
    # INSERT INTO USERS (user_name, user_full_name, email_addr, password, profile_img_loc, skill_lvl, acc_creation_date)
    # VALUES
    # ('{user_name}', '{name}', '{email_addr}', '{password}', '{profile_img}', {skill_lvl}, '{acc_date}')
    # '''


conn = pg.connect(database="postgres",
                    host="localhost",
                    user="postgres",
                    password="postgres",
                    port="5432")

# print(",\n".join(values))

query = f'''
    INSERT INTO PICKLEBALL.USERS (user_name, user_full_name, email_addr, password, profile_img_loc, skill_lvl, acc_creation_date)
    VALUES
    {",\n".join(values)}
    '''
    
   
conn.autocommit = True 
    
cur = conn.cursor()
cur.execute(query)

conn.commit()
# cur.fetchall()



