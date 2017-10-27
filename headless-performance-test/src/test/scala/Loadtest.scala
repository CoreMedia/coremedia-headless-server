import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class Loadtest extends Simulation {

  val httpConf = http
    .baseURL(System.getProperty("loadtest.baseUrl"))
    .acceptHeader("application/json,application/json;charset=UTF-8")
    .acceptLanguageHeader("de,en-US;q=0.7,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:48.0) Gecko/20100101 Firefox/48.0")

  val concurrency = System.getProperty("loadtest.concurrency")
  val duration = System.getProperty("loadtest.duration")
  val rampup = System.getProperty("loadtest.rampup")
  val name = System.getProperty("loadtest.scenario")

  val urls = csv(System.getProperty("loadtest.urlFile")).circular

  val scn = scenario(name)
    .during(duration.toInt seconds) {
      feed(urls)
        .exec {
          http("request").get("${url}")
        }
    }

  setUp(scn.inject(rampUsers(concurrency.toInt) over (rampup.toInt seconds))).protocols(httpConf)
}
