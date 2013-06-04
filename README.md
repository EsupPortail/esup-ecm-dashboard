# esup-ecm-dashboard

Nuxeo dashboard elements integrated into the portal as a portlet based on esup-commons V2.

## Installation

1. Download last version of portlet war from [esup maven repository](https://mvn.esup-portail.org/content/repositories/releases/org/esupportail/esup-portlet-intranet-web-springmvc-portlet/).
2. From uPortal launch ant portlet.deploy -DportletApp=/path/to/war/portlet.war.
3. As uPortal administrator register this new portlet.

## Preferences

+ nuxeoHost(Nuxeo URL) : editable, ex:) http://localhost:8080/nuxeo
+ nuxeoPortalAuthSecret(Secret used by Nuxeo [portal-sso](http://www.esup-portail.org/pages/viewpage.action?pageId=201097232) authentication layer) : No editable, ex:) ITJDrjUWLGZ1fNSil795
+ NXQL(Order NXQL spent Nuxeo) : editable, ex:) SELECT * FROM Document WHERE ecm:path STARTSWITH '/testfolder1/'
+ maxPageSize (Max number of entries per page) : editable, ex:) 20
+ columns (Column list in JSON format) : editable, ex:) ["dc:title", "dc:description", "dc:modified"]

============
"Editable" is useful uniquely if uPortal administrator add edit capability to the portlet.
In this case, if uPortal administrator configure a preference as read-only=false, this preference will be editable with the portlet edit view.

## Maven actions
+ Packages generation :
mvn clean package

+ Releases management :
mvn release:prepare -DautoVersionSubmodules
mvn release:perform -Dgoals=package

+ Prototyping :
portlet :
 mvn portlet-prototyping:run
 and go on http://localhost:9090/pluto
