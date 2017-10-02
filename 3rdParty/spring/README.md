Spring
=================
Overview
---------------
Spring is a java framework created by Rod Johnson (http://www.springsource.org/)
in 2003.  It claims to be a comprehensive solution and is best used when writing
large, monolithic web apps.  This approach is no longer the recommended best
practice.  Instead, microservices are more in favor.  Spring suffers from being
intellectually burdensome.  It exposes many thousands of methods and any one
of them may introduce an unexpected side-effect.  Also, beans, which are
effectively singletons are considered an anti-pattern and reflective of use of
global variables.  Be very cautious when leveraging this framework.  Once the
conventions are understood, one can argue that spring is not that bad, but
the readability suffers when functionality moves from java code to xml
configuration files (which is what spring encourages).
