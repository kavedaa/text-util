package no.vedaadata.text

class StringComparer:
  def process(x: String) = x
  def compare(a: String, b: String) = process(a) == process(b)

trait CaseInsensitive extends StringComparer:
  private def toLowerCase(x: String) = x.toLowerCase
  override def process(x: String) = toLowerCase(super.process(x))

trait TransformLineBreaksToWhiteSpace extends StringComparer:
  private def transform(x: String) = x.replaceAll("[\r\n]+", " ")
  override def process(x: String) = transform(super.process(x))

trait NormalizeWhitespace extends StringComparer:
  private def normalize(x: String) = x.replaceAll("[ ]+", " ")
  override def process(x: String) = normalize(super.process(x))

trait TrimWhitespace extends StringComparer:
  private def trim(x: String) = x.replaceAll("[ ]+", "")
  override def process(x: String) = trim(super.process(x))