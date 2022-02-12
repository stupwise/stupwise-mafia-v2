package stupwise.websocket

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{jawn, Decoder, Encoder, Error}
import stupwise.websocket.Protocol._

trait Codecs {
  implicit val withDiscriminatorConfig: Configuration = Configuration.default.withDiscriminator("type")

  implicit val incomeMessageDecoder: Decoder[InMessage]   = deriveConfiguredDecoder
  implicit val outcomeMessageEncoder: Encoder[OutMessage] = deriveConfiguredEncoder
  implicit val eventDecoder: Decoder[Event]               = deriveConfiguredDecoder
}

object Codecs {
  def decode(json: String)(implicit d: Decoder[InMessage]): Either[Error, InMessage] =
    jawn.decode[InMessage](json)

  def encode(outcomeMessage: OutMessage)(implicit e: Encoder[OutMessage]): String =
    outcomeMessage.asJson.noSpaces
}
