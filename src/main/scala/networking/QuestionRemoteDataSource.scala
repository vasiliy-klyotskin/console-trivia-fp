package org.kyotskin.trivia
package networking

import cats.effect.{IO, Resource}
import org.http4s.{EntityDecoder, Method, Request, Uri}
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import org.http4s.circe.jsonOf
import io.circe.generic.auto.*
import scala.util.Random
import domain.*

case class QuestionRemoteDTO(
  id: String,
  question: QuestionTextRemoteDTO,
  correctAnswer: String,
  incorrectAnswers: List[String],
  difficulty: String,
  category: String
)

case class QuestionTextRemoteDTO(text: String)

implicit val loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]
implicit val questionDecoder: EntityDecoder[IO, QuestionRemoteDTO] = jsonOf[IO, QuestionRemoteDTO]
implicit val questionTextDecoder: EntityDecoder[IO, QuestionTextRemoteDTO] = jsonOf[IO, QuestionTextRemoteDTO]

def createHttpClient(): Resource[IO, Client[IO]] = {
  EmberClientBuilder.default[IO].build
}

def fetchRemoteQuestions(client: Resource[IO, Client[IO]])(url: String): IO[Option[List[QuestionRemoteDTO]]] = {
  val request = Request[IO](Method.GET, Uri.unsafeFromString(url))
  client.use { client =>
    client.expect(request)(jsonOf[IO, List[QuestionRemoteDTO]])
  }.attempt.map(_.toOption)
}

def toQuestionsModel(remotes: Option[List[QuestionRemoteDTO]]): Option[Questions] = {
  remotes.map(_.map(remote =>
    val possibleAnswers = remote.incorrectAnswers.appended(remote.correctAnswer)
    Question(
      text = remote.question.text,
      possibleAnswers = Random.shuffle(possibleAnswers),
      correctAnswer = remote.correctAnswer
    )
  ))
}