// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package lucuma

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import lucuma.mt.mousetrap.mod.ExtendedKeyboardEvent
import react.common._

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

package object mousetrap {

  val Mousetrap = lucuma.mt.mousetrap.mod.^
  type CallbackFn = js.Function2[ /* e */ ExtendedKeyboardEvent, /* combo */ String, js.Any]

  implicit class CallbackOps(cb: CallbackTo[Boolean]) {
    def toCBF: CallbackFn = (_: ExtendedKeyboardEvent, _: String) => cb.runNow()
  }
}

package mousetrap {
  final case class KeyBinding(keys: List[String], action: CallbackTo[Boolean])

  object KeyBinding {
    def apply(key: String, action: => Callback): KeyBinding =
      KeyBinding(List(key), action.map(_ => true))

    def apply(key: List[String], action: => Callback): KeyBinding =
      KeyBinding(key, action.map(_ => true))
  }

  /**
   * Wraps a component in some keyboard bindings
   */
  final case class ReactMousetrap(
    bindings: List[KeyBinding]
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
        Callback($.props.bindings.foreach(b => Mousetrap.bind(b.keys.toJSArray, b.action.toCBF)))
      }
      .componentWillUnmount { $ =>
        Callback($.props.bindings.foreach(b => Mousetrap.unbind(b.keys.toJSArray)))
      }
      .build
  }
}
