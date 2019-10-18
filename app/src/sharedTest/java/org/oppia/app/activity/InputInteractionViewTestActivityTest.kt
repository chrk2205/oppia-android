package org.oppia.app.activity

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.oppia.app.R
import org.oppia.app.model.InteractionObject
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions.typeText
import com.google.common.truth.Truth.assertThat
import org.oppia.app.customview.interaction.NumericInputInteractionView

/** Tests for [InputInteractionViewTestActivity]. */
@RunWith(AndroidJUnit4::class)
class InputInteractionViewTestActivityTest {
  @get:Rule
  var activityTestRule: ActivityTestRule<InputInteractionViewTestActivity> = ActivityTestRule(
    InputInteractionViewTestActivity::class.java, /* initialTouchMode= */ true, /* launchActivity= */ false
  )

  @Test
  fun testNumericInputInteractionView_withNoInputText_hasCorrectPendingAnswerType() {
    val activityScenario = ActivityScenario.launch(InputInteractionViewTestActivity::class.java)
    activityScenario.onActivity { activity ->
      val textAnswerRetriever =
        activity.findViewById(R.id.test_number_input_interaction_view) as NumericInputInteractionView
      assertThat(textAnswerRetriever.getPendingAnswer()).isInstanceOf(InteractionObject::class.java)
      assertThat(textAnswerRetriever.getPendingAnswer().real).isEqualTo(0.0)
    }
  }

  @Test
  fun testNumericInputInteractionView_withInputtedText_hasCorrectPendingAnswer() {
    val activityScenario = ActivityScenario.launch(InputInteractionViewTestActivity::class.java)
    onView(withId(R.id.test_number_input_interaction_view)).perform(typeText("9"))
    activityScenario.onActivity { activity ->
      val textAnswerRetriever =
        activity.findViewById(R.id.test_number_input_interaction_view) as NumericInputInteractionView
      assertThat(textAnswerRetriever.getPendingAnswer()).isInstanceOf(InteractionObject::class.java)
      assertThat(textAnswerRetriever.getPendingAnswer().objectTypeCase).isEqualTo(InteractionObject.ObjectTypeCase.REAL)
      assertThat(textAnswerRetriever.getPendingAnswer().real).isEqualTo(9.0)
    }
  }

  @Test
  fun testNumericInputInteractionView_withInputtedText_hasCorrectPendingAnswerWithDecimalValues() {
    val activityScenario = ActivityScenario.launch(InputInteractionViewTestActivity::class.java)
    onView(withId(R.id.test_number_input_interaction_view)).perform(typeText("9.5"))
    activityScenario.onActivity { activity ->
      val textAnswerRetriever =
        activity.findViewById(R.id.test_number_input_interaction_view) as NumericInputInteractionView
      assertThat(textAnswerRetriever.getPendingAnswer().objectTypeCase).isEqualTo(InteractionObject.ObjectTypeCase.REAL)
      assertThat(textAnswerRetriever.getPendingAnswer().real).isEqualTo(9.5)
    }
  }
}
