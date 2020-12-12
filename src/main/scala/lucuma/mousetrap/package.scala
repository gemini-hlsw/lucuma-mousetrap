package lucuma

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import react.common._
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import lucuma.mt.mousetrap.mod.ExtendedKeyboardEvent

package object mousetrap {

  val Mousetrap = lucuma.mt.mousetrap.mod.^
  type CallbackFn = js.Function2[ /* e */ ExtendedKeyboardEvent, /* combo */ String, Unit]

  implicit class CallbackOps(cb: Callback) {
    def toCBF: CallbackFn = (_: ExtendedKeyboardEvent, _: String) => cb.runNow()
  }
}

package mousetrap {

  /**
   * Wraps a component in some keyboard bindings
   */
  final case class ReactMousetrap(
    bindings: Map[String, Callback]
  ) extends ReactPropsWithChildren[ReactMousetrap](ReactMousetrap.component) {
    @inline def render: Seq[CtorType.ChildArg] => VdomElement =
      ReactMousetrap.component(this)
  }

  object ReactMousetrap {
    type Props = ReactMousetrap

    val component = ScalaComponent
      .builder[Props]
      .stateless
      .render_C(c => c)
      .componentDidMount { $ =>
        Callback($.props.bindings.foreach { case (k, cb) => Mousetrap.bind(k, cb.toCBF) })
      }
      .componentWillUnmount { $ =>
        Callback($.props.bindings.foreach { case (k, _) => Mousetrap.unbind(k) })
      }
      .build
  }
}
