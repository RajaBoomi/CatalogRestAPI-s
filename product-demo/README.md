<div id="readme" class="Box adoc js-code-block-container Box--responsive">
    <div class="Box-header d-flex flex-items-center flex-justify-between bg-white border-bottom-0">
      <h2 class="Box-title pr-3">
        README.adoc
      </h2>
    </div>
      <div class="Box-body px-5 pb-5">
        <article class="markdown-body entry-content container-lg" itemprop="text"><div id="user-content-toc">
<div id="user-content-toctitle">Table of Contents</div>
<ul>
<li><a href="#what-youll-build">What You’ll Build</a></li>
<li><a href="#initial">Create a WebFlux Handler</a></li>
<li><a href="#create-a-router">Create a Router</a></li>
<li><a href="#make-the-application-executable">Make the Application Executable</a></li>
<li><a href="#test-the-application">Test the Application</a></li>
<li><a href="#summary">Summary</a></li>
</ul>
</div>
<div>
<p>This guide walks you through the process of creating a "Product CRUD" RESTful web
service with Spring WebFlux (new as of version 5) using functional programming approach.</p>
</div>
<div>
<table>
<tbody><tr>
<td>
<div>Note</div>
</td>
<td>
This guide shows the functional way of using Spring WebFlux. You can also
<a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-controller" rel="nofollow">use annotations with WebFlux</a>.
</td>
</tr>
</tbody></table>
</div>
<div>
<h2 id="user-content-what-youll-build"><a id="user-content-what-youll-build" class="anchor" aria-hidden="true" href="#what-youll-build"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>What You’ll Build</h2>
<div>
<div>
<p>You’ll build a RESTful product CRUD web services with Spring Webflux. You’ll be able to see output with the below url:</p>
</div>
<div>
<div>
<pre><code>http://localhost:8080/</code></pre>
</div>
</div>
</div>
</div>

<div>
<h2 id="user-content-initial"><a id="user-content-create-a-webflux-handler" class="anchor" aria-hidden="true" href="#create-a-webflux-handler"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Create a WebFlux Handler</h2>
<div>
<div>
<p>In the Spring Reactive approach, we use a handler to handle the request and create a
response, as shown in the following example:</p>
</div>
<div>
<p><code>src/main/java/com/products/demo/handler/ProductHandler.java</code></p>
</div>
<div>
<div>
</div>
<div>
<p>This simple reactive class displays stream of items from a database and so on. Note the reactive code: a <code>Mono</code> object that holds
a <code>ServerResponse</code> body.</p>
</div>
</div>
</div>
<div>
<h2 id="user-content-create-a-router"><a id="user-content-create-a-router" class="anchor" aria-hidden="true" href="#create-a-router"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Create a Router</h2>
<div>
<div>
<p>In this application, we use a router to handle the route as
shown in the following example:</p>
</div>
<div>
<p><code>src/main/java/com/products/demo/config/ProductRouter.java</code></p>
</div>
<div>
<p>The router listens for traffic on the <code>/</code> path and returns the value provided by our
reactive handler class.</p>
</div>
</div>
</div>
<div>
<h2 id="user-content-make-the-application-executable"><a id="user-content-make-the-application-executable" class="anchor" aria-hidden="true" href="#make-the-application-executable"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Make the Application Executable</h2>
<div>
<div>
<p>Although it is possible to package this service as a traditional
<a href="/spring-guides/gs-reactive-rest-service/blob/master/understanding/WAR">WAR</a> file for deployment to an external application server,
the simpler approach demonstrated below creates a standalone application. You package
everything in a single, executable JAR file, driven by a good old Java <code>main()</code> method.
Along the way, you use Reactive Spring’s support for embedding the Netty server as the
HTTP runtime, instead of deploying to an external instance.</p>
</div>
<div>
<p><code>src/main/java/com/products/demo/ProductApp.java</code></p>
</div>
<div>
</div>
</div>
</div>
<div>
<h2 id="user-content-test-the-application"><a id="user-content-test-the-application" class="anchor" aria-hidden="true" href="#test-the-application"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Test the Application</h2>
<div>
<div>
<p>Now that the application is running, you can test it. To start with, you can open a
browser and go to <code><a href="http://localhost:8080/" rel="nofollow">http://localhost:8080/</a></code> and see, "Product CRUD" For this guide,
we also created a test class to get you started on testing with the WebTestClient class.</p>
</div>
<div>
<p><code>src/test/java/com/products/demo/ProductHandlerTest.java</code></p>
</div>
    <div>
    <table>
  <tr><td>List Products</td><td>	GET</td>	<td>/products</td></tr>
<tr><td>Find product by product id	</td><td>GET</td><td>	/products/{productid}</td></tr>
<tr><td>Search product by title</td><td>	GET</td><td>	/products/search?title={title}</td></tr>
<tr><td>Search product by description</td><td>	GET	</td><td>/products/search?description={description}</td></tr>
        <tr><td>Batch Insert</td><td>	POST</td><td>	/products/bulk</td><td>{
   
    "title": "Notebook 14 Core i5 10th Gen",
    "description": "32 inch Full HD LED Backlit Anti-glare Display",
    "brand": "Mi",
    "price": 49999,
    "color": "Silver"
    
}</td></tr>
<tr><td>Delete product</td><td>	DELETE</td><td>	/products/{productid}</td></tr>
<tr><td>Update product</td><td>	PUT</td><td>	/products/{productid}</td></tr>
<tr><td>Sort by</td><td>	GET	</td><td>/products?sortname={fieldname}&type={type}<br> [fieldname: brand/price/color, type:ASC/DESC]</td></tr>
<tr><td>Pagination</td><td> 	GET</td><td>	/products?page={page}&size={size}</td></tr>
<tr><td>Versioned API</td><td> GET</td><td> /products/version - Add header API-Version=1.0.0 or 1.0.1</td></tr>
</table>
    </div>
    <h4>Building docker image</h4>
    spring-boot:docker-image
<div>
</div>
</div>
</div>
<div>
<h2 id="user-content-summary"><a id="user-content-summary" class="anchor" aria-hidden="true" href="#summary"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Summary</h2>
<div>
<div>
<p>Reactive Spring application for product CRUD completed</p>
</div>
</div>
</div></article>
      </div>
  </div>
