package com.khallware.poc.retrofit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Credentials;
import java.util.List;

/**
 *
 * POC_MAVEN_REPO=/tmp/foo
 * rm -rf $POC_MAVEN_REPO
 * 
 * for dep in com.squareup.retrofit2:retrofit:2.3.0 \
 *       com.squareup.okhttp3:okhttp:3.9.0 \
 *       com.squareup.okio:okio:1.13.0 \
 *       com.fasterxml.jackson.core:jackson-databind:2.9.1 \
 *       com.fasterxml.jackson.core:jackson-core:2.9.1 \
 *       com.fasterxml.jackson.core:jackson-annotations:2.9.1 \
 *       com.squareup.retrofit2:converter-jackson:2.3.0; do
 *    mvn -Dmaven.repo.local=$POC_MAVEN_REPO \
 *        org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
 *        -DrepoUrl=url \
 *        -Dartifact=$dep
 * done
 * RETROFIT_JAR=$(find $POC_MAVEN_REPO -name \*retrofit\*jar)
 * JAR1=$(find $POC_MAVEN_REPO -name \*converter\*jar)
 * JAR2=$(find $POC_MAVEN_REPO -name \*okhttp\*jar)
 * javac -d /tmp -cp $RETROFIT_JAR:$JAR1:$JAR2 3rdParty/retrofit/Main.java
 * echo 'Main-Class: com.khallware.poc.retrofit.Main' >/tmp/manifest
 * jar cmvf /tmp/manifest /tmp/retrofit-poc.jar -C /tmp com
 *
 * JAR3=$(find $POC_MAVEN_REPO -name \*okio\*jar)
 * JAR4=$(find $POC_MAVEN_REPO -name \*databind\*jar)
 * JAR5=$(find $POC_MAVEN_REPO -name \*jackson-core\*jar)
 * JAR6=$(find $POC_MAVEN_REPO -name \*jackson-annotations\*jar)
 * export CLASSPATH=/tmp/retrofit-poc.jar:$RETROFIT_JAR:$JAR1:$JAR2:$JAR3:$JAR4:$JAR5:$JAR6
 * java -cp $CLASSPATH com.khallware.poc.retrofit.Main https://localhost:8080/
 * rm -rf /tmp/com /tmp/manifest /tmp/junit-poc.jar
 *
 */
public class Main
{
	public static interface YoutrackSvc
	{
		@Headers("Accept: application/json")
		@GET("httpAuth/app/rest/projects")
		Call<List<Project>> getProjects(
			@Header("Authorization") String creds);

		@Headers("Accept: application/json")
		@GET("httpAuth/app/rest/projects/{project}/buildTypes")
		Call<List<BuildType>> getBuildTypes(
			@Header("Authorization") String creds,
			@Path("project") String project);
	}

	public static class Project
	{
		private String id = "";
		private String name = "";
		private String description = "";
		private String webUrl = "";
		private String href = "";

		public String getId()
		{
			return(id);
		}

		@Override
		public String toString()
		{
			return(name);
		}
	}

	public static class BuildType
	{
		private String id = "";
		private String name = "";
		private String description = "";
		private String projectName = "";
		private String projectId = "";
		private String webUrl = "";
		private String href = "";

		@Override
		public String toString()
		{
			return(name);
		}
	}

	public static void main(String... args) throws Exception
	{
		final String creds = Credentials.basic(args[1], args[2]);
		final String mime = "application/json";
		OkHttpClient client = new OkHttpClient().newBuilder()
			.addInterceptor((chain) -> {
				return(chain.proceed(
					chain.request().newBuilder()
						.header("Authorization", creds)
						.build()
					)
				);
			})
			.build();
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(args[0])
			.addConverterFactory(JacksonConverterFactory.create())
			.client(client)
			.build();
		YoutrackSvc svc = retrofit.create(YoutrackSvc.class);

		for (Project proj : svc.getProjects(creds).execute().body()) {
			System.out.println("PROJECT: "+proj);

			for (BuildType type : svc.getBuildTypes(
					creds, proj.getId()).execute().body()) {
				System.out.println("\tBUILD TYPE: "+type);
			}
		}
	}
}
