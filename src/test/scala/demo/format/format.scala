package demo.format

import no.vedaadata.text.Format

@main def main =

  summon[Format.BooleanFormat]
  summon[Format.ByteFormat]
  summon[Format.DateFormatter]

