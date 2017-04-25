# Flashcloud bus timetable software
## Documentation
Javadoc can be found on the [github pages](https://hjfitz.github.io/inse-v2), hjfitz.github.io/inse-v2 - or in docs/. The code has more comments in further detail that javadoc wouldn't have picked up.

## Running
add stuff here

## To Dev
You need to install the following:
- Window Builder
- Swing Designer
- SWT Designer
They can be installed by opening Eclipse (VERSION NEON), pressing help and pressing 'install new software'. Add "http://download.eclipse.org/windowbuilder/WB/integration/4.4/" as a link and select the boxes, and install.

You will also need to ensure that you've got the correct database setup:
- Use the schema `mysql -u root -p < schema.sql`
- Set the root password to 'root'
	- You can change the password in DatabaseConnection.java if you'd rather not change your MySQL password.
