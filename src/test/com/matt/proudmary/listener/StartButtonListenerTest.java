package com.matt.proudmary.listener;

import android.view.View;
import android.widget.EditText;
import org.junit.Test;
import android.content.Context;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created with IntelliJ IDEA.
 * User: mouldm02
 * Date: 23/02/2014
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */

@RunWith(MockitoJUnitRunner.class)
public class StartButtonListenerTest {

	@Mock
	View mockView;
	@Mock
	Context mockContext;

	@Test
	public void testUpdateTextMethodUpdatesTest() {
		StartButtonListener startButtonListener = new StartButtonListener();
		EditText recipientNumberView = new EditText(mockContext);
//		when(mockView).thenReturn(recipientNumberView);

//		startButtonListener.onClick(mockView);

//		assertEquals(recipientNumberView.getText().toString(), StartButtonListener.SERVICE_STARTED);
	}
}
